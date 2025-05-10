package net.thedragonskull.crystalmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrystalMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CRYSTAL_TAB = CREATIVE_MODE_TABS.register("crystal_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_AMETHYST_POWDER.get()))
                    .title(Component.translatable("creativetab.crystal_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.MORTAR_ITEM.get());

                        pOutput.accept(Items.AMETHYST_SHARD);
                        pOutput.accept(ModItems.RAW_AMETHYST_POWDER.get());

                        pOutput.accept(ModItems.CAVANSITE_SHARD.get());
                        pOutput.accept(ModItems.RAW_CAVANSITE_POWDER.get());
                        pOutput.accept(ModBlocks.CAVANSITE_BLOCK.get());
                        pOutput.accept(ModBlocks.BUDDING_CAVANSITE.get());
                        pOutput.accept(ModBlocks.SMALL_CAVANSITE_BUD.get());
                        pOutput.accept(ModBlocks.MEDIUM_CAVANSITE_BUD.get());
                        pOutput.accept(ModBlocks.LARGE_CAVANSITE_BUD.get());
                        pOutput.accept(ModBlocks.CAVANSITE_CLUSTER.get());

                        pOutput.accept(ModItems.SULFUR_SHARD.get());
                        pOutput.accept(ModBlocks.SULFUR_CLUSTER.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
