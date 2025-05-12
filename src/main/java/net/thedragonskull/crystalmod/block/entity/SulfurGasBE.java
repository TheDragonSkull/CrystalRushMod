package net.thedragonskull.crystalmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.thedragonskull.crystalmod.util.BlockSearchUtil.hasNearbyBlock;

public class SulfurGasBE extends BlockEntity {
    private int tickCounter;

    public SulfurGasBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SULFUR_GAS_BE.get(), pPos, pBlockState);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        tickCounter++;

        if (tickCounter > 600 + level.getRandom().nextInt(200)) {
            level.removeBlock(worldPosition, false);
            return;
        }

        if (!hasNearbyBlock(level, worldPosition, Blocks.LAVA, 3) ||
                !hasNearbyBlock(level, worldPosition, Blocks.BASALT, 3) ||
                    hasNearbyBlock(level, worldPosition, Blocks.WATER, 3) ) {
            tickCounter += 5;
        }

    }

    public void moveGas() {
        if (level == null) return;

        for (Direction dir : Direction.values()) {
            BlockPos newPos = worldPosition.relative(dir);

            if (level.isEmptyBlock(newPos)) {
                level.setBlockAndUpdate(newPos, getBlockState());

                BlockState state = getBlockState();
                level.setBlock(newPos, state, 3);
                BlockEntity newBE = level.getBlockEntity(newPos);

                if (newBE instanceof SulfurGasBE gasBE) {
                    gasBE.tickCounter = this.tickCounter;
                }

                return;
            }
        }

    }


}
