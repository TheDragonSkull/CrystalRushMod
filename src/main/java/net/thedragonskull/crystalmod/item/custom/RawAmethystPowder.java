package net.thedragonskull.crystalmod.item.custom;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.thedragonskull.crystalmod.sound.ModSounds;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;

public class RawAmethystPowder extends Item {

    public RawAmethystPowder(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {

        if (pLivingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(this, 20);
        }

        if (!pLevel.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) pLevel;

            DustParticleOptions purpleDust = new DustParticleOptions(
                    new Vector3f(0.4549f, 0.2667f, 0.7686f), 1.0f
            );

            for (int i = 0; i < 20; i++) { // todo test multiplayer
                serverLevel.sendParticles(purpleDust,
                        pLivingEntity.getX(), pLivingEntity.getY() + 1.5D, pLivingEntity.getZ(),
                        1, 0.2, 0.2, 0.2, 0.01);
            }

            Collection<MobEffectInstance> activeEffects = new ArrayList<>(pLivingEntity.getActiveEffects());

            for (MobEffectInstance effectInstance : activeEffects) {
                MobEffect effect = effectInstance.getEffect();

                if (effect.isInstantenous()) continue;
                if (effect.getCategory() == MobEffectCategory.NEUTRAL) continue;

                boolean isBeneficial = effect.getCategory() == MobEffectCategory.BENEFICIAL;
                int amplifier = effectInstance.getAmplifier();
                int duration = effectInstance.getDuration();

                if (isBeneficial) {
                    int newAmplifier = Math.min(amplifier + 1, 4);
                    int newDuration = duration / 2;

                    pLivingEntity.removeEffect(effect);
                    pLivingEntity.addEffect(new MobEffectInstance(effect, newDuration, newAmplifier, true, true, true));

                } else if (amplifier > 0) {
                    int newAmplifier = amplifier - 1;
                    int newDuration = duration * 2;

                    pLivingEntity.removeEffect(effect);
                    pLivingEntity.addEffect(new MobEffectInstance(effect, newDuration, newAmplifier, true, true, true));
                }

            }


        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 5;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return ModSounds.SNIFF.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }
}
