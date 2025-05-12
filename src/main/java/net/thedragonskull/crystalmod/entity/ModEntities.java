package net.thedragonskull.crystalmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;
import net.thedragonskull.crystalmod.entity.custom.ThrownSulfurBottle;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CrystalMod.MOD_ID);

    public static final RegistryObject<EntityType<ThrownSulfurBottle>> THROWN_SULFUR_BOTTLE =
            ENTITY_TYPES.register("thrown_sulfur_bottle", () -> EntityType.Builder.<ThrownSulfurBottle>of(ThrownSulfurBottle::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("thrown_sulfur_bottle"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
