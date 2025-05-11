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

        // Close lava (<= 3)
        if (!hasNearby(level, pos, Blocks.LAVA, 3)) return;

        // Close water (<= 10)
        if (hasNearby(level, pos, Blocks.WATER, 3)) return;

        if (random.nextInt(20) != 0) return;

        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos targetPos = pos.relative(direction);

        if (!level.getBlockState(targetPos).isAir()) return;

        BlockState sulfur = ModBlocks.SULFUR_CLUSTER.get().defaultBlockState()
                .setValue(AmethystClusterBlock.FACING, direction);

        level.setBlockAndUpdate(targetPos, sulfur);
    }

    private boolean hasNearby(LevelAccessor level, BlockPos center, Block targetBlock, int radius) {
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    check.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    if (level.getBlockState(check).is(targetBlock)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasVisibleNearby(LevelAccessor level, BlockPos start, Block targetBlock, int radius) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();

            if (start.distManhattan(current) > radius) continue;

            if (level.getBlockState(current).is(targetBlock)) {
                return true;
            }

            for (Direction dir : Direction.values()) {
                BlockPos next = current.relative(dir);
                if (!visited.contains(next) && level.getBlockState(next).isAir()) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }

        return false;
    }


}
