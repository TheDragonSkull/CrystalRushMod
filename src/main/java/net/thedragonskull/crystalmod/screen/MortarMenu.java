package net.thedragonskull.crystalmod.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.thedragonskull.crystalmod.block.ModBlocks;
import net.thedragonskull.crystalmod.block.entity.MortarBE;

public class MortarMenu extends AbstractContainerMenu {
    public final MortarBE mortarBE;
    private final Level level;
    private final ContainerData data;

    public MortarMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public MortarMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MORTAR_MENU.get(), pContainerId);
        checkContainerSize(inv, 1);
        mortarBE = ((MortarBE) entity);
        this.level = inv.player.level();
        this.data = data;
        this.mortarBE.setDataAccess(data);

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.mortarBE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler
                -> this.addSlot(new SlotItemHandler(iItemHandler, 0, 62, 55)));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 11;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot fromSlot = getSlot(index);
        ItemStack fromStack = fromSlot.getItem();

        if (fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if (!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if (index < 36) {
            if (!moveItemStackTo(fromStack, 36, 37, false)) {
                return ItemStack.EMPTY;
            }
            mortarBE.resetProgress();
            //todo play put sound

        } else if (index == 36) {
            if (!moveItemStackTo(fromStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
            mortarBE.resetProgress();
            //todo play take sound

        } else {
            System.err.println("Invalid slot index: " + index);
            return ItemStack.EMPTY;
        }

        fromSlot.onTake(player, fromStack);
        return copyFromStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, mortarBE.getBlockPos()),
                pPlayer, ModBlocks.MORTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
