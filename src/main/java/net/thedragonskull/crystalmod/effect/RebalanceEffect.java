package net.thedragonskull.crystalmod.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class RebalanceEffect extends InstantenousMobEffect {

    protected RebalanceEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (entity.level().isClientSide()) return;

        Collection<MobEffectInstance> activeEffects = new ArrayList<>(entity.getActiveEffects());

        for (MobEffectInstance effectInstance : activeEffects) {
            MobEffect effect = effectInstance.getEffect();

            if (effect.isInstantenous()) continue;
            if (effect.getCategory() == MobEffectCategory.NEUTRAL) continue;

            boolean isBeneficial = effect.getCategory() == MobEffectCategory.BENEFICIAL;
            int originalAmplifier = effectInstance.getAmplifier();
            int originalDuration = effectInstance.getDuration();

            if (isBeneficial) {
                if (originalAmplifier < 4) {
                    int newAmplifier = originalAmplifier + 1;
                    int newDuration = Math.max(1, (int)(originalDuration * 0.75f));

                    entity.removeEffect(effect);
                    entity.addEffect(new MobEffectInstance(effect, newDuration, newAmplifier, true, true, true));
                }

            } else if (originalAmplifier > 0) {
                int newAmplifier = originalAmplifier - 1;
                int newDuration = Math.max(1, (int)(originalDuration * 1.25f));

                entity.removeEffect(effect);
                entity.addEffect(new MobEffectInstance(effect, newDuration, newAmplifier, true, true, true));
            }
        }

    }
}
