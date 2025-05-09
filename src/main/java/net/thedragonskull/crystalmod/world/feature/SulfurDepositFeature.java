package net.thedragonskull.crystalmod.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.thedragonskull.crystalmod.block.ModBlocks;

public class SulfurDepositFeature extends Feature<NoneFeatureConfiguration> {

    public SulfurDepositFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        for (int i = 0; i < 96; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );

            if (level.getBlockState(pos).isAir() &&
                    level.getBlockState(pos.below()).is(Blocks.BASALT)) {

                level.setBlock(pos, ModBlocks.SMALL_CAVANSITE_BUD.get().defaultBlockState(), 2);
            }
        }

        return true;
    }
}
