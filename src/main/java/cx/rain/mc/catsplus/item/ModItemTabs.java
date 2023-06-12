package cx.rain.mc.catsplus.item;

import cx.rain.mc.catsplus.CatsPlus;
import cx.rain.mc.catsplus.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItemTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CatsPlus.MODID);

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }

    public static final RegistryObject<CreativeModeTab> CATS_PLUS = TABS.register("catsplus_group", () -> CreativeModeTab.builder()
            .title(Component.translatable(Constants.TAB_CATS_PLUS))
            .icon(() -> new ItemStack(ModItems.CAT_BAG.get()))
            .displayItems(((parameters, output) -> {
                output.accept(ModItems.CAT_BAG.get());
                output.accept(ModItems.TOTEMEOW.get());
            }))
            .build());
}
