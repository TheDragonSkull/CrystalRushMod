package net.thedragonskull.testmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.testmod.TestMod;
import net.thedragonskull.testmod.block.ModBlocks;
import net.thedragonskull.testmod.item.custom.MortarItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);

    public static final RegistryObject<Item> MORTAR_ITEM = ITEMS.register("mortar",
            () -> new MortarItem(ModBlocks.MORTAR.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
