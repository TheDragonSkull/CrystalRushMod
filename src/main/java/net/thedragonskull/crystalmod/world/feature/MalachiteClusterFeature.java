package net.thedragonskull.crystalmod.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class MalachiteClusterFeature extends Feature<SimpleBlockConfiguration> {

    public MalachiteClusterFeature(Codec<SimpleBlockConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        LevelAccessor level = context.level();
        RandomSource random = context.random();
        BlockState toPlace = context.config().toPlace().getState(random, BlockPos.ZERO);

        int placed = 0;

        for (int i = 0; i < 40; i++) {
            BlockPos pos = context.origin().offset(
                    random.nextInt(8) - 4,
                    random.nextInt(4) - 2,
                    random.nextInt(8) - 4
            );

            BlockState state = level.getBlockState(pos);
            if (!isValidCopper(state.getBlock())) continue;

            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                BlockState targetState = level.getBlockState(targetPos);

                if (!targetState.isAir()) continue;
                if (random.nextFloat() > 0.5f) continue;

                BlockState placedState = toPlace;

                if (placedState.hasProperty(BlockStateProperties.FACING)) {
                    placedState = placedState.setValue(BlockStateProperties.FACING, dir);
                }

                level.setBlock(targetPos, placedState, 2);
                placed++;
            }
        }

        return placed > 0;
    }

    private boolean isValidCopper(Block block) {
        return (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE);
    }
}
