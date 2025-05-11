package net.thedragonskull.crystalmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.thedragonskull.crystalmod.util.BlockSearchUtil.hasNearbyBlock;

public class SulfurGasBE extends BlockEntity {
    private int tickCounter;
    private int fadeStartTick = -1;
    private static final int FADE_DURATION = 40;

    public SulfurGasBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SULFUR_GAS_BE.get(), pPos, pBlockState);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        tickCounter++;

        if (tickCounter > 600 + level.getRandom().nextInt(200)) {
            startFading();
        }

        if (!hasNearbyBlock(level, worldPosition, Blocks.LAVA, 3) ||
                !hasNearbyBlock(level, worldPosition, Blocks.BASALT, 3)) {
            startFading();
        }

        if (fadeStartTick != -1) {
            if (tickCounter >= fadeStartTick + FADE_DURATION) {
                level.removeBlock(worldPosition, false);
            }
        }

    }

    private void startFading() {
        if (fadeStartTick == -1) {
            fadeStartTick = tickCounter;
            setChanged();
        }
    }
}
