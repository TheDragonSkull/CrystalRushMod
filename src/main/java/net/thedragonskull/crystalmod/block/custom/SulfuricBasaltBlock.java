package net.thedragonskull.crystalmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.util.BlockSearchUtil;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import static net.minecraft.world.level.block.BuddingAmethystBlock.canClusterGrowAtState;

public class SulfuricBasaltBlock extends Block {
    private static final Direction[] DIRECTIONS = Direction.values();

    public SulfuricBasaltBlock(Properties pProperties) {
        super(pProperties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        // Only below Y=0
        if (pos.getY() >= 0) return;

        // Close to lava (<= 3)
        if (!BlockSearchUtil.hasNearbyBlock(level, pos, Blocks.LAVA, 3)) return;

        // Not close to water (<= 3)
        if (BlockSearchUtil.hasNearbyBlock(level, pos, Blocks.WATER, 3)) return;

        if (random.nextInt(20) != 0) return;

        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos targetPos = pos.relative(direction);

        if (!level.getBlockState(targetPos).isAir()) return;

        BlockState sulfur = ModBlocks.SULFUR_CLUSTER.get().defaultBlockState()
                .setValue(AmethystClusterBlock.FACING, direction);

        level.setBlockAndUpdate(targetPos, sulfur);
    }

}
