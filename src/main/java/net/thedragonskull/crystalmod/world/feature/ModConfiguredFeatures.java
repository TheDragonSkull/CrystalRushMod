package net.thedragonskull.crystalmod.world.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_DEPOSIT_KEY = registerKey("sulfur_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALACHITE_CLUSTER_KEY = registerKey("malachite_cluster");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVANSITE_GEODE_KEY = registerKey("cavansite_geode");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        register(context, SULFUR_DEPOSIT_KEY,
                ModFeatures.SULFUR_CLUSTER_FEATURE.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SULFUR_CLUSTER.get()))
        );

        register(context, MALACHITE_CLUSTER_KEY,
                ModFeatures.MALACHITE_CLUSTER_FEATURE.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.MALACHITE_CLUSTER.get()))
        );

        register(context, CAVANSITE_GEODE_KEY, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.AIR),  // Empty blocks
                        BlockStateProvider.simple(ModBlocks.CAVANSITE_BLOCK.get()),  // Outer Layer
                        BlockStateProvider.simple(ModBlocks.BUDDING_CAVANSITE.get()),  // Inside block
                        BlockStateProvider.simple(Blocks.CALCITE),  // Filler
                        BlockStateProvider.simple(Blocks.SMOOTH_BASALT),  // Secondary block
                        List.of(ModBlocks.SMALL_CAVANSITE_BUD.get().defaultBlockState(),
                                ModBlocks.MEDIUM_CAVANSITE_BUD.get().defaultBlockState(),
                                ModBlocks.LARGE_CAVANSITE_BUD.get().defaultBlockState(),
                                ModBlocks.CAVANSITE_CLUSTER.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),  // Layer config
                        new GeodeCrackSettings(0.95D, 2.0D, 2),  // Crack config
                        0.35D, 0.083D,  // Crack chance
                        true, UniformInt.of(4, 6),  // Size uniformity
                        UniformInt.of(3, 4), UniformInt.of(1, 2),  // Layers
                        -16, 16, 0.05D, 1));

    }

        public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CrystalMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration
    ) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
