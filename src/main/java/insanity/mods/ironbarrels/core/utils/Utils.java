package insanity.mods.ironbarrels.core.utils;

import insanity.mods.ironbarrels.IronBarrelType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Utils extends Slot {

    public Utils(IInventory par1iInventory, int par2, int par3, int par4, IronBarrelType type) {
        super(par1iInventory, par2, par3, par4);
    }

    public static void loadItemStacksFromNBT(NBTTagList tag, ItemStack[] stacks) {
        for (int i = 0; i < tag.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = tag.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < stacks.length) {
                stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public static NBTTagList writeItemStacksToNBT(ItemStack[] stacks) {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < stacks.length; ++i) {
            if (stacks[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stacks[i].writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        return list;
    }
}