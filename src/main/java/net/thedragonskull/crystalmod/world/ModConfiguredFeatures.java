package net.thedragonskull.crystalmod.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_DEPOSIT_KEY = registerKey("sulfur_deposit");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        PlacedFeature sulfurBudPlacedFeature = new PlacedFeature(
                Holder.direct(new ConfiguredFeature<>(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(ModBlocks.SMALL_CAVANSITE_BUD.get()) // todo cambiar
                        )
                )),
                List.of(
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.allOf(
                                        BlockPredicate.matchesBlocks(Blocks.AIR),
                                        BlockPredicate.anyOf(
                                                BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.BASALT),
                                                BlockPredicate.matchesBlocks(new BlockPos(0,  1, 0), Blocks.BASALT),
                                                BlockPredicate.matchesBlocks(new BlockPos( 1, 0, 0), Blocks.BASALT),
                                                BlockPredicate.matchesBlocks(new BlockPos(-1, 0, 0), Blocks.BASALT),
                                                BlockPredicate.matchesBlocks(new BlockPos(0, 0,  1), Blocks.BASALT),
                                                BlockPredicate.matchesBlocks(new BlockPos(0, 0, -1), Blocks.BASALT)
                                        )
                                )
                        )

                )
        );

        RandomPatchConfiguration sulfurPatchConfig = new RandomPatchConfiguration(
                96,
                7,
                3,
                Holder.direct(sulfurBudPlacedFeature)
        );

        register(context, SULFUR_DEPOSIT_KEY, Feature.RANDOM_PATCH, sulfurPatchConfig);
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
