package net.thedragonskull.crystalmod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.entity.ModEntities;
import net.thedragonskull.crystalmod.item.ModItems;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class ThrownSulfurBottle extends ThrowableItemProjectile {

    public ThrownSulfurBottle(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownSulfurBottle(Level pLevel) {
        super(ModEntities.THROWN_SULFUR_BOTTLE.get(), pLevel);
    }

    public ThrownSulfurBottle(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.THROWN_SULFUR_BOTTLE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SULFUR_DIOXIDE_BOTTLE.get();
    }

    @Override
    protected float getGravity() {
        return 0.07F;
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if (!level().isClientSide()) {
            BlockPos startPos;

            if (pResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) pResult;
                startPos = blockHit.getBlockPos().relative(blockHit.getDirection());

            } else if (pResult.getType() == HitResult.Type.ENTITY) {
                startPos = BlockPos.containing(this.getPosition(0)).below();

            } else {
                this.discard();
                return;
            }

            Level level = level();
            Queue<BlockPos> queue = new ArrayDeque<>();
            Set<BlockPos> visited = new HashSet<>();

            queue.add(startPos);
            visited.add(startPos);

            int placed = 0;

            while (!queue.isEmpty() && placed < 6) {
                BlockPos current = queue.poll();

                if (level.getBlockState(current).isAir()) {
                    level.setBlock(current, ModBlocks.SULFUR_GAS.get().defaultBlockState(), 3);
                    placed++;
                }

                for (Direction dir : Direction.values()) {
                    BlockPos adjacent = current.relative(dir);
                    if (!visited.contains(adjacent) && level.getBlockState(adjacent).isAir()) {
                        queue.add(adjacent);
                        visited.add(adjacent);
                    }
                }
            }

            level.playSound(null, this.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F); //todo test multiplayer
            ((ServerLevel)this.level()).sendParticles(
                    new ItemParticleOption(ParticleTypes.ITEM, Items.GLASS_BOTTLE.getDefaultInstance()),
                    this.getX(), this.getY(), this.getZ(),
                    8, 0.1D, 0.1D, 0.1D, 0.05D
            );

            level.playSound(null, startPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            this.discard();
        }
    }

}
