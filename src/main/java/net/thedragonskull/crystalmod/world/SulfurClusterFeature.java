package net.thedragonskull.crystalmod.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

import static net.minecraft.world.level.block.AmethystClusterBlock.FACING;

public class SulfurClusterFeature extends Feature<SimpleBlockConfiguration> {

    public SulfurClusterFeature(Codec<SimpleBlockConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        LevelAccessor level = context.level();
        RandomSource random = context.random();
        Block block = context.config().toPlace().getState(random, BlockPos.ZERO).getBlock();
        int placed = 0;

        for (int i = 0; i < 16; i++) {
            BlockPos pos = context.origin().offset(
                    random.nextInt(8) - 4,
                    random.nextInt(4) - 2,
                    random.nextInt(8) - 4
            );

            if (!level.getBlockState(pos).isAir()) continue;

            for (Direction dir : Direction.values()) {
                BlockPos adjacent = pos.relative(dir.getOpposite());
                if (level.getBlockState(adjacent).is(Blocks.BASALT)) { // TODO: basalt y blackstone / cerca de la lava <= 3 blocks
                    BlockState state = block.defaultBlockState().setValue(FACING, dir);
                    level.setBlock(pos, state, 2);
                    placed++;
                    break;
                }
            }
        }

        return placed > 0;
    }

}
