package net.thedragonskull.crystalmod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.thedragonskull.crystalmod.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.thedragonskull.crystalmod.util.BlockSearchUtil.hasNearbyWater;

@Mixin(BlockBehaviour.class)
public abstract class CopperMalachiteRandomTickMixin {

    private static final Direction[] DIRECTIONS = Direction.values();

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void crystalmod$growMalachite(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {

        if (!isValidCopperBlock(state)) return;

        if (!hasNearbyWater(level, pos, 3)) return;

        if (!passesRandomChance(state, random)) return;

        Direction dir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos targetPos = pos.relative(dir);

        if (!level.getBlockState(targetPos).isAir()) return;

        level.setBlock(
                targetPos,
                ModBlocks.MALACHITE_CLUSTER.get()
                        .defaultBlockState()
                        .setValue(AmethystClusterBlock.FACING, dir),
                3
        );
    }

    private static boolean isValidCopperBlock(BlockState state) {
        return state.is(Blocks.COPPER_ORE) || state.is(Blocks.DEEPSLATE_COPPER_ORE) || state.is(Blocks.RAW_COPPER_BLOCK);
    }

    private static boolean passesRandomChance(BlockState state, RandomSource random) {
        if (state.is(Blocks.RAW_COPPER_BLOCK)) {
            return random.nextInt(40) == 0;
        }

        return random.nextInt(20) == 0;
    }
}
