package net.thedragonskull.crystalmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.thedragonskull.crystalmod.block.ModBlocks;

public class BuddingCavansiteBlock extends BuddingAmethystBlock {
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingCavansiteBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

        if (pRandom.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            Block block = null;

            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_CAVANSITE_BUD.get();

            } else if (blockstate.is(ModBlocks.SMALL_CAVANSITE_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.MEDIUM_CAVANSITE_BUD.get();

            } else if (blockstate.is(ModBlocks.MEDIUM_CAVANSITE_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = Blocks.LARGE_AMETHYST_BUD;

            } else if (blockstate.is(Blocks.LARGE_AMETHYST_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.CAVANSITE_CLUSTER.get();
            }

            if (block != null) {
                BlockState blockstate1 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)
                        .setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);

                pLevel.setBlockAndUpdate(blockpos, blockstate1);
            }

        }
    }
}
