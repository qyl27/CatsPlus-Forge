package cx.rain.mc.catsplus.item;

import cx.rain.mc.catsplus.CatsPlus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CatsPlus.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final RegistryObject<Item> CAT_BAG = ITEMS.register("cat_bag", () -> new CatBagItem(new Item.Properties().fireResistant().stacksTo(1)));
    public static final RegistryObject<Item> TOTEMEOW = ITEMS.register("totemeow", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));


}
