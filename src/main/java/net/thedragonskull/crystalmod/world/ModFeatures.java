package net.thedragonskull.crystalmod.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, CrystalMod.MOD_ID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> SULFUR_CLUSTER_FEATURE =
            FEATURES.register("sulfur_cluster_feature",
                    () -> new SulfurClusterFeature(SimpleBlockConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
