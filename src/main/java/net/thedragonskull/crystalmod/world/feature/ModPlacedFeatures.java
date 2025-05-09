package net.thedragonskull.crystalmod.world.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, CrystalMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> SULFUR_DEPOSIT = PLACED_FEATURES.register("sulfur_deposit",
            () -> new PlacedFeature(
                    ModConfiguredFeatures.SULFUR_DEPOSIT_CONFIGURED.getHolder().get(),
                    List.of(
                            PlacementUtils.HEIGHTMAP,
                            RarityFilter.onAverageOnceEvery(2),
                            InSquarePlacement.spread(),
                            BiomeFilter.biome()
                    )
            ));

    public static void register(IEventBus bus) {
        PLACED_FEATURES.register(bus);
    }
}
