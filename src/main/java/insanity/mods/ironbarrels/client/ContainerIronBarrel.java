package insanity.mods.ironbarrels.client;

import insanity.mods.ironbarrels.IronBarrelType;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@ChestContainer(isLargeChest = false)
public class ContainerIronBarrel extends Container {
    private final IronBarrelType type;
    private final EntityPlayer player;
    private final IInventory barrel;

    public ContainerIronBarrel(IInventory playerInventory, IInventory barrelInventory, IronBarrelType type, int xSize, int ySize) {
        barrel = barrelInventory;
        player = ((InventoryPlayer) playerInventory).player;
        this.type = type;
        barrelInventory.openInventory();
        layoutContainer(playerInventory, barrelInventory, type, xSize, ySize);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return barrel.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < type.size) {
                if (!mergeItemStack(itemstack1, type.size, inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemstack1, 0, type.size, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        barrel.closeInventory();
    }

    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, IronBarrelType type, int xSize, int ySize) {
        {
            for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++) {
                for (int chestCol = 0; chestCol < type.getRowLength(); chestCol++) {
                    addSlotToContainer(type.makeSlot(chestInventory, chestCol + chestRow * type.getRowLength(), 8 + chestCol * 18, 18 + chestRow * 18));
                }
            }
        }

        int leftCol = (xSize - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18
                        - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    @ChestContainer.RowSizeCallback
    public int getNumColumns() {
        return type.getRowLength();
    }
}
