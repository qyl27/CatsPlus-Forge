package cx.rain.mc.catsplus.client;

import cx.rain.mc.catsplus.CatsPlus;
import cx.rain.mc.catsplus.item.ModItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CatsPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterColorHandlersEventListener {

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex <= 0 && stack.getItem() instanceof DyeableLeatherItem dyeable) {
                return dyeable.getColor(stack);
            }
            return -1;
        }, ModItems.CAT_BAG.get());
    }
}
