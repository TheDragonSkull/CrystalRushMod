package net.thedragonskull.crystalmod.world.biomemods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;

public class ModBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CrystalMod.MOD_ID);

    public static final RegistryObject<Codec<SulfurBiomeModifier>> SULFUR_MODIFIER =
            BIOME_MODIFIERS.register("sulfur_addition", () ->
                    RecordCodecBuilder.create(builder -> builder.group(
                            Biome.LIST_CODEC.fieldOf("biomes").forGetter(SulfurBiomeModifier::biomes),
                            PlacedFeature.CODEC.fieldOf("feature").forGetter(SulfurBiomeModifier::feature)
                    ).apply(builder, SulfurBiomeModifier::new))
            );

    public static void register(IEventBus eventBus) {
        BIOME_MODIFIERS.register(eventBus);
    }
}
