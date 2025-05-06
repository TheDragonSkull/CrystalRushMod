package net.thedragonskull.crystalmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.item.custom.MortarItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CrystalMod.MOD_ID);

    public static final RegistryObject<Item> MORTAR_ITEM = ITEMS.register("mortar",
            () -> new MortarItem(ModBlocks.MORTAR.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
