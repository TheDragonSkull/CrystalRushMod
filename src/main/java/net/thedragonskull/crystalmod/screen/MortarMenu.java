package net.thedragonskull.crystalmod.screen;

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
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot fromSlot = this.slots.get(pIndex);

        if (fromSlot.hasItem()) {
            ItemStack slotStack = fromSlot.getItem();
            returnStack = slotStack.copy();

            if (pIndex < 1) { //In the expositor inventory
                if (!this.moveItemStackTo(slotStack, 1, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, 1, false)) { //In the hotbar/player inv
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                fromSlot.setByPlayer(ItemStack.EMPTY);
            } else {
                fromSlot.setChanged();
            }

        }
        return returnStack;
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
