package net.thedragonskull.crystalmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.thedragonskull.crystalmod.block.ModBlocks;

import static net.thedragonskull.crystalmod.util.BlockSearchUtil.hasNearbyBlock;

public class SulfurClusterBlock extends AmethystClusterBlock {

    public SulfurClusterBlock(int pSize, int pOffset, Properties pProperties) {
        super(pSize, pOffset, pProperties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int gasToGenerate = 2 + random.nextInt(3);
        BlockState gasState = ModBlocks.SULFUR_GAS.get().defaultBlockState();

        BlockPos.MutableBlockPos startPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 20; i++) {
            startPos.setWithOffset(pos,
                    random.nextInt(7) - 3,
                    random.nextInt(5) - 2,
                    random.nextInt(7) - 3
            );

            if (level.isEmptyBlock(startPos) &&
                    hasNearbyBlock(level, startPos, Blocks.LAVA, 3) &&
                    hasNearbyBlock(level, startPos, Blocks.BASALT, 3)) {

                level.setBlockAndUpdate(startPos, gasState);
                gasToGenerate--;

                BlockPos currentPos = startPos.immutable();
                for (Direction dir : Direction.values()) {
                    if (gasToGenerate <= 0) break;

                    BlockPos adjacent = currentPos.relative(dir);
                    if (level.isEmptyBlock(adjacent)) {
                        level.setBlockAndUpdate(adjacent, gasState);
                        gasToGenerate--;
                    }
                }

                break;
            }
        }

        super.randomTick(state, level, pos, random);
    }

}
