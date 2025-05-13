package net.thedragonskull.crystalmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.crystalmod.CrystalMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CrystalMod.MOD_ID);

    public static final RegistryObject<MobEffect> REBALANCE_EFFECT = MOB_EFFECTS.register("thedragon_rebalance",
            () -> new RebalanceEffect(MobEffectCategory.BENEFICIAL, 0x7444c4));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
