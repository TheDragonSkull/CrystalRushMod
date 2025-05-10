package net.thedragonskull.crystalmod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.custom.BuddingCavansiteBlock;
import net.thedragonskull.crystalmod.block.custom.Mortar;
import net.thedragonskull.crystalmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CrystalMod.MOD_ID);

    public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar",
            () -> new Mortar(BlockBehaviour.Properties.copy(Blocks.DIORITE).strength(1.5F, 6.0F)
                    .noOcclusion().requiresCorrectToolForDrops()));

    // CAVANSITE

    public static final RegistryObject<Block> CAVANSITE_BLOCK = registerBlock("cavansite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5F).sound(SoundType.GLASS).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BUDDING_CAVANSITE = registerBlock("budding_cavansite",
            () -> new BuddingCavansiteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).randomTicks()
                    .strength(1.5F).sound(SoundType.GLASS).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CAVANSITE_CLUSTER = registerBlock("cavansite_cluster",
            () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).forceSolidOn()
                    .noOcclusion().randomTicks().sound(SoundType.GLASS).strength(1.5F)
                    .lightLevel((state) -> 5).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> LARGE_CAVANSITE_BUD = registerBlock("large_cavansite_bud",
            () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(ModBlocks.CAVANSITE_CLUSTER.get()).forceSolidOn()
                    .sound(SoundType.GLASS).lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> MEDIUM_CAVANSITE_BUD = registerBlock("medium_cavansite_bud",
            () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(ModBlocks.CAVANSITE_CLUSTER.get()).forceSolidOn()
                    .sound(SoundType.GLASS).lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SMALL_CAVANSITE_BUD = registerBlock("small_cavansite_bud",
            () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(ModBlocks.CAVANSITE_CLUSTER.get()).forceSolidOn()
                    .sound(SoundType.GLASS).lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));

    // SULFUR

    public static final RegistryObject<Block> SULFUR_CLUSTER = registerBlock("sulfur_cluster",
            () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).forceSolidOn()
                    .noOcclusion().randomTicks().sound(SoundType.GLASS).strength(1.5F)
                    .lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY)));


        private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
