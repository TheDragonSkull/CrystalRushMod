package net.thedragonskull.crystalmod.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.thedragonskull.crystalmod.CrystalMod;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SULFUR_DEPOSIT_PLACED_KEY = registerKey("sulfur_deposit_placed");
    public static final ResourceKey<PlacedFeature> MALACHITE_CLUSTER_PLACED = registerKey("malachite_cluster_placed");

    public static final ResourceKey<PlacedFeature> CAVANSITE_GEODE_PLACED_KEY = registerKey("cavansite_geode_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, SULFUR_DEPOSIT_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SULFUR_DEPOSIT_KEY),
                List.of(
                        CountPlacement.of(12),
                        RarityFilter.onAverageOnceEvery(2), // 50% chance
                        HeightRangePlacement.uniform(
                                VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.belowTop(0)
                        ),
                        BiomeFilter.biome()
                ));

        register(context, MALACHITE_CLUSTER_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MALACHITE_CLUSTER_KEY),
                List.of(
                        CountPlacement.of(300),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.top()
                        ),
                        BiomeFilter.biome()
                )
        );

        register(context, CAVANSITE_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CAVANSITE_GEODE_KEY), List.of(
                RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(CrystalMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, modifiers));
    }
}
