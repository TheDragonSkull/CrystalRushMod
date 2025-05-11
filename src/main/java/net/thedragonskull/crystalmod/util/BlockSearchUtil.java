package net.thedragonskull.crystalmod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class BlockSearchUtil {

    public static boolean hasNearbyBlock(LevelAccessor level, BlockPos center, Block targetBlock, int radius) {
        BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    checkPos.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    if (level.getBlockState(checkPos).is(targetBlock)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
