package net.thedragonskull.crystalmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.thedragonskull.crystalmod.recipe.MortarRecipe;
import net.thedragonskull.crystalmod.screen.MortarMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Optional;


public class MortarBE extends BlockEntity implements GeoBlockEntity, MenuProvider {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation GRINDING = RawAnimation.begin().thenLoop("animation.mortar.grinding");
    public int useCooldownTicks = 0;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            resetProgress();
            setChanged();

            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (!getStackInSlot(slot).isEmpty()) {
                return stack;
            }

            if (stack.getCount() > 1) {
                ItemStack remainder = stack.copy();
                remainder.setCount(stack.getCount() - 1);

                if (!simulate) {
                    ItemStack oneItem = stack.copy();
                    oneItem.setCount(1);
                    setStackInSlot(slot, oneItem);
                }

                return remainder;
            } else {
                if (!simulate) {
                    setStackInSlot(slot, stack.copy());
                }
                return ItemStack.EMPTY;
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private LazyOptional<IItemHandler> lazyItemStackHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 77;
    private ContainerData dataAccess;

    public MortarBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MORTAR_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MortarBE.this.progress;
                    case 1 -> MortarBE.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MortarBE.this.progress = pValue;
                    case 1 -> MortarBE.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public boolean isCrafting() {
        return dataAccess != null && dataAccess.get(0) > 0;
    }

    public int getProgress() {
        return dataAccess != null ? dataAccess.get(0) : 0;
    }

    public int getMaxProgress() {
        return dataAccess != null ? dataAccess.get(1) : 0;
    }

    public void setDataAccess(ContainerData dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ItemStack getSlotItem() {
        return this.itemStackHandler.getStackInSlot(0);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemStackHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemStackHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemStackHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());

        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "mortar_controller", state -> PlayState.CONTINUE)
        .triggerableAnim("grinding", GRINDING));
    }

    public void triggerUseAnimation(){
        triggerAnim("mortar_controller", "grinding");
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    public void tick(Level pLevel1, BlockPos pPos, BlockState pState1) {
        if (useCooldownTicks > 0) {
            useCooldownTicks--;
        } else {
            stopTriggeredAnimation("mortar_controller", "grinding");
            //todo stop sound
        }
    }

    public void resetProgress() {
        progress = 0;
    }

    public boolean hasRecipe() {
        Optional<MortarRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        ItemStack stackInSlot = itemStackHandler.getStackInSlot(0);
        ItemStack result = recipe.get().getResultItem(level.registryAccess());

        if (ItemStack.isSameItemSameTags(stackInSlot, result)) {
            return false;
        }

        return true;
    }


    private Optional<MortarRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemStackHandler.getSlots());

        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemStackHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(MortarRecipe.Type.INSTANCE, inventory, level);
    }

    public void craftItem() {
        Optional<MortarRecipe> recipe = getCurrentRecipe();

        ItemStack result = recipe.get().getResultItem(level.registryAccess()).copy();
        itemStackHandler.setStackInSlot(0, result);
    }

    public boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    public void increaseCraftingProgress() {
        progress++;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.crystalmod.mortar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MortarMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("mortar.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("mortar.progress");
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag() != null)
            load(pkt.getTag());
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}
