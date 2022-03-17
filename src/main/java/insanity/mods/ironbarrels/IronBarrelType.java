package insanity.mods.ironbarrels;

import insanity.mods.ironbarrels.core.utils.Utils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public enum IronBarrelType {
    IRON(54, 9),
    GOLD(81, 9),
    DIAMOND(108, 12),
    OBSIDIAN(108, 12);

    private final int rowLength;
    public int size;

    IronBarrelType(int size, int rowLength) {
        this.size = size;
        this.rowLength = rowLength;
    }

    public int getRowCount() {
        return size / rowLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public Slot makeSlot(IInventory chestInventory, int index, int x, int y) {
        return new Utils(chestInventory, index, x, y, this);
    }
}
