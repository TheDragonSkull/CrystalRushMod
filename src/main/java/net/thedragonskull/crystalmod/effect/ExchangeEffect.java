package net.thedragonskull.crystalmod.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ExchangeEffect extends InstantenousMobEffect {

    public ExchangeEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity entity, int pAmplifier, double pHealth) {
        if (entity.level().isClientSide()) return;
        if (!(entity instanceof Player player)) return;

        float hp = player.getHealth();
        int hunger = player.getFoodData().getFoodLevel();

        if (hp <= 10 && hunger >= 10) {
            player.setHealth(hp + 4);
            player.getFoodData().setFoodLevel(hunger - 4);
        } else if (hp >= 10 && hunger <= 10) {
            player.setHealth(hp - 4);
            player.getFoodData().setFoodLevel(hunger + 4);
        }
    }

/*    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (entity.level().isClientSide()) return;
        if (!(entity instanceof Player player)) return;

        float hp = player.getHealth();
        int hunger = player.getFoodData().getFoodLevel();

        if (hp <= 10 && hunger >= 10) {
            player.setHealth(hp + 4);
            player.getFoodData().setFoodLevel(hunger - 4);
        } else if (hp >= 10 && hunger <= 10) {
            player.setHealth(hp - 4);
            player.getFoodData().setFoodLevel(hunger + 4);
        }
    }*/
}
