package net.thedragonskull.crystalmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.thedragonskull.crystalmod.block.entity.ModBlockEntities;
import net.thedragonskull.crystalmod.block.entity.MortarBE;
import net.thedragonskull.crystalmod.block.entity.SulfurGasBE;
import net.thedragonskull.crystalmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class SulfurGasBlock extends HalfTransparentBlock implements EntityBlock {

    public SulfurGasBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((SulfurGasBE)blockEntity).tick();
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pLevel.isClientSide || !(pEntity instanceof Player player)) return;

        BlockPos headPos = player.blockPosition().above();

        if (pPos.equals(headPos)) {

            if (!player.hasEffect(MobEffects.POISON)) {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, false, false));
            }

            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false, false));
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if (!(entity instanceof SulfurGasBE)) {
                throw new IllegalStateException("Our Container provider is missing!");
            }

            if (pPlayer.getMainHandItem().is(Items.GLASS_BOTTLE)) {
                ItemStack handItem = pPlayer.getItemInHand(pHand);

                handItem.shrink(1);
                pPlayer.addItem(new ItemStack(ModItems.MORTAR_ITEM.get()));
                pLevel.removeBlock(pPos, false);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pOldState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {

        if (!pLevel.isClientSide && pOldState.getBlock() instanceof SulfurGasBlock && !pNewState.isAir() && pNewState.getBlock() != pOldState.getBlock()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof SulfurGasBE gasBE) {
                gasBE.moveOrDie();
            }
        }

        super.onRemove(pOldState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        return  Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return true;
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection) {
        return pAdjacentState.is(this) || super.skipRendering(pState, pAdjacentState, pDirection);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SulfurGasBE(pPos, pState);
    }
}
