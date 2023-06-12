package cx.rain.mc.catsplus.item;

import cx.rain.mc.catsplus.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CatBagItem extends Item implements DyeableLeatherItem {
    public static final String TAG_CAT_NAME = "Cat";

    public CatBagItem(Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        if (!stack.hasTag() || stack.getTag() == null || !stack.getTag().contains(TAG_CAT_NAME)) {
            tooltipComponents.add(Component.translatable(Constants.ITEM_CAT_BAG_DESC_NO_CAT).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltipComponents.add(Component.translatable(Constants.ITEM_CAT_BAG_DESC_HAS_CAT).withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var stack = context.getItemInHand();
        if (!stack.hasTag()) {
            return InteractionResult.PASS;
        }

        var tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_CAT_NAME)) {
            return InteractionResult.PASS;
        }

        var level = context.getLevel();
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        var catTag = tag.getCompound(TAG_CAT_NAME);
        var pos = context.getClickLocation();

        var posList = new ListTag();
        posList.add(DoubleTag.valueOf(pos.x()));
        posList.add(DoubleTag.valueOf(pos.y()));
        posList.add(DoubleTag.valueOf(pos.z()));
        catTag.put("Pos", posList);

        var serverLevel = (ServerLevel) level;
        var cat = EntityType.CAT.create(serverLevel, catTag, null,
                context.getClickedPos().relative(context.getClickedFace()),
                MobSpawnType.EVENT, true, false);

        var player = context.getPlayer();
        if (cat != null) {
            cat.load(catTag);
            serverLevel.addFreshEntity(cat);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }

        tag.remove(TAG_CAT_NAME);
        stack.setTag(tag);
        player.setItemInHand(context.getHand(), stack);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
                                                  LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!(interactionTarget instanceof Cat cat)) {
            return InteractionResult.PASS;
        }

        if (stack.hasTag() && stack.getTag() != null && stack.getTag().contains(TAG_CAT_NAME)) {
            return InteractionResult.PASS;
        }

        if (!cat.isOwnedBy(player)) {
            return InteractionResult.FAIL;
        }

        var tag = stack.getOrCreateTag();
        var catTag = new CompoundTag();
        interactionTarget.save(catTag);
        tag.put(TAG_CAT_NAME, catTag);
        stack.setTag(tag);
        player.setItemInHand(usedHand, stack);
        cat.discard();
        return InteractionResult.SUCCESS;
    }
}
