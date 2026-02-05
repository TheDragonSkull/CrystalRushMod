package net.thedragonskull.crystalmod.effect;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RebalanceEffect extends InstantenousMobEffect {

    protected RebalanceEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (entity.level().isClientSide()) return;
        RandomSource randomSource = RandomSource.create();

        List<MobEffectInstance> activeEffects = new ArrayList<>(entity.getActiveEffects());

        MobEffectInstance effectInstance = activeEffects.get(randomSource.nextInt(activeEffects.size()));
        MobEffect effect = effectInstance.getEffect();

        if (effect.isInstantenous()) return;
        if (effect.getCategory() == MobEffectCategory.NEUTRAL) return;

        boolean isBeneficial = effect.getCategory() == MobEffectCategory.BENEFICIAL;
        int originalAmplifier = effectInstance.getAmplifier();
        int originalDuration = effectInstance.getDuration();

        if (isBeneficial) {
            if (originalAmplifier < 4) {
                int newAmplifier = originalAmplifier + 1;

                entity.removeEffect(effect);
                entity.addEffect(new MobEffectInstance(effect, originalDuration, newAmplifier, effectInstance.isAmbient(), effectInstance.isVisible(), effectInstance.showIcon()));
            }

        } else if (originalAmplifier > 0) {
            int newAmplifier = originalAmplifier - 1;

            entity.removeEffect(effect);
            entity.addEffect(new MobEffectInstance(effect, originalDuration, newAmplifier, true, true, true));
        }

    }
}
