package insanity.mods.ironbarrels.tileentities;

import insanity.mods.ironbarrels.IronBarrelType;
import insanity.mods.ironbarrels.blocks.BlockIronBarrel;
import insanity.mods.ironbarrels.client.ContainerIronBarrel;
import insanity.mods.ironbarrels.core.utils.Utils;
import insanity.mods.ironbarrels.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class TileEntityIronBarrel extends TileEntity implements IInventory {
    private static IronBarrelType type;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private float soundTimer;
    private ItemStack[] chestContents;

    public TileEntityIronBarrel() {
        this(IronBarrelType.IRON);
    }

    protected TileEntityIronBarrel(IronBarrelType type) {
        super();
        TileEntityIronBarrel.type = type;
        this.chestContents = new ItemStack[getSizeInventory()];
    }

    public static IronBarrelType getType() {
        return type;
    }

    @Override
    public int getSizeInventory() {
        return type.size;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && p_70300_1_.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        Utils.loadItemStacksFromNBT(nbttaglist, this.chestContents);
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setTag("Items", Utils.writeItemStacksToNBT(this.chestContents));
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return this.chestContents[p_70301_1_];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        if (this.chestContents[p_70298_1_] != null) {
            ItemStack itemstack;

            if (this.chestContents[p_70298_1_].stackSize <= p_70298_2_) {
                itemstack = this.chestContents[p_70298_1_];
                this.chestContents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            itemstack = this.chestContents[p_70298_1_].splitStack(p_70298_2_);

            if (this.chestContents[p_70298_1_].stackSize == 0) {
                this.chestContents[p_70298_1_] = null;
            }

            this.markDirty();
            return itemstack;
        }
        return null;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        if (this.chestContents[p_70304_1_] != null) {
            ItemStack itemstack = this.chestContents[p_70304_1_];
            this.chestContents[p_70304_1_] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        this.chestContents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public void updateEntity() {
        ++this.ticksSinceSync;
        float f;

        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - f, this.yCoord - f, this.zCoord - f, this.xCoord + 1 + f, this.yCoord + 1 + f, this.zCoord + 1 + f));
            for (EntityPlayer entityplayer : list) {
                if (entityplayer.openContainer instanceof ContainerIronBarrel) {
                    ++this.numPlayersUsing;
                }
            }
        }

        f = 0.1F;
        double d2;
        if (this.numPlayersUsing > 0 && this.soundTimer <= 0.0F) {
            double d1 = this.xCoord + 0.5D;
            d2 = this.zCoord + 0.5D;
            this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) % 8 + 8, 2);
            this.worldObj.playSoundEffect(d1, this.yCoord + 0.5D, d2, Reference.MOD_ID + ":block.barrel.open", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.soundTimer > 0.0F || this.numPlayersUsing > 0 && this.soundTimer < 1.0F) {
            float f1 = 0.5F;
            if (this.numPlayersUsing > 0) {
                this.soundTimer += f1;
            } else {
                this.soundTimer -= f1;
            }
            if (this.soundTimer > 10) {
                this.soundTimer = 10;
            }
            if (this.soundTimer < f1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) > 7) {
                this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) % 8, 2);
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, Reference.MOD_ID + ":block.barrel.close", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            if (this.soundTimer < 0) {
                this.soundTimer = 0;
            }
        }
    }

    @Override
    public String getInventoryName() {
        return type.name();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
        if (p_145842_1_ == 1) {
            this.numPlayersUsing = p_145842_2_;
            return true;
        }
        return super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    @Override
    public void openInventory() {
        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        if (this.getBlockType() instanceof BlockIronBarrel) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }
}
