package cx.rain.mc.catsplus;

import cx.rain.mc.catsplus.item.CatBagItem;
import cx.rain.mc.catsplus.item.ModItemTabs;
import cx.rain.mc.catsplus.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Mod(CatsPlus.MODID)
public class CatsPlus {
    public static final String MODID = "catsplus";
    public static final String NAME = "CatsPlus";

    private static CatsPlus INSTANCE;

    private Logger logger = LoggerFactory.getLogger(NAME);

    public CatsPlus() {
        INSTANCE = this;

        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onSetup);
        bus.addListener(this::onClientSetup);

        ModItems.register(bus);
        ModItemTabs.register(bus);
    }

    public static CatsPlus getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }

    public void onSetup(FMLCommonSetupEvent event) {

    }

    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.CAT_BAG.get(), new ResourceLocation(MODID, "cat"),
                    (stack, clientLevel, livingEntity, i) -> {
                        if(!stack.hasTag()
                                || stack.getTag() == null
                                || !stack.getTag().contains(CatBagItem.TAG_CAT_NAME)) {
                            return 0F;
                        }

                        var nbt = stack.getTag().getCompound(CatBagItem.TAG_CAT_NAME);
                        return switch (nbt.getString("variant")) {
                            case "minecraft:tabby" -> 0.05F;
                            case "minecraft:black" -> 0.1F;
                            case "minecraft:red" -> 0.15F;
                            case "minecraft:siamese" -> 0.2F;
                            case "minecraft:british_shorthair" -> 0.25F;
                            case "minecraft:calico" -> 0.3F;
                            case "minecraft:persian" -> 0.35F;
                            case "minecraft:ragdoll" -> 0.4F;
                            case "minecraft:white" -> 0.45F;
                            case "minecraft:jellie" -> 0.5F;
                            case "minecraft:all_black" -> 0.55F;
                            default -> 1F;
                        };
                    });
        });
    }
}
