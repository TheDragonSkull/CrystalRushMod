package net.thedragonskull.crystalmod.world;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.world.feature.ModPlacedFeatures;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_SULFUR_DEPOSIT = registerKey("add_sulfur_deposit");
    public static final ResourceKey<BiomeModifier> ADD_MALACHITE_CLUSTER = registerKey("add_malachite_cluster");
    public static final ResourceKey<BiomeModifier> ADD_CAVANISTE_GEODE = registerKey("add_cavansite_geode");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_SULFUR_DEPOSIT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BASALT_DELTAS)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SULFUR_DEPOSIT_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_MALACHITE_CLUSTER,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MALACHITE_CLUSTER_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));

        context.register(ADD_CAVANISTE_GEODE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CAVANSITE_GEODE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(CrystalMod.MOD_ID, name));
    }
}
