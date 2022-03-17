package insanity.mods.ironbarrels.core.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import insanity.mods.ironbarrels.IronBarrelType;
import insanity.mods.ironbarrels.client.ContainerIronBarrel;
import insanity.mods.ironbarrels.client.GUIbarrel;
import insanity.mods.ironbarrels.lib.GUIsID;
import insanity.mods.ironbarrels.lib.Reference;
import insanity.mods.ironbarrels.tileentities.TileEntityDiamondBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityGoldBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityIronBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityObsidianBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    public void registerEntities() {
        GameRegistry.registerTileEntity(TileEntityIronBarrel.class, Reference.MOD_ID + "." + "ironBarrel");
        GameRegistry.registerTileEntity(TileEntityGoldBarrel.class, Reference.MOD_ID + "." + "goldBarrel");
        GameRegistry.registerTileEntity(TileEntityDiamondBarrel.class, Reference.MOD_ID + "." + "diamondBarrel");
        GameRegistry.registerTileEntity(TileEntityObsidianBarrel.class, Reference.MOD_ID + "." + "obsidianBarrel");
    }

    public void registerRenderers() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        switch (ID) {
            case GUIsID.IRON_BARREL:
                return new ContainerIronBarrel(player.inventory, (TileEntityIronBarrel) te, TileEntityIronBarrel.getType(), 0, 0);
            case GUIsID.GOLD_BARREL:
                return new ContainerIronBarrel(player.inventory, (TileEntityGoldBarrel) te, TileEntityGoldBarrel.getType(), 0, 0);
            case GUIsID.DIAMOND_BARREL:
                return new ContainerIronBarrel(player.inventory, (TileEntityDiamondBarrel) te, TileEntityDiamondBarrel.getType(), 0, 0);
            case GUIsID.OBSIDIAN_BARREL:
                return new ContainerIronBarrel(player.inventory, (TileEntityObsidianBarrel) te, TileEntityObsidianBarrel.getType(), 0, 0);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        switch (ID) {
            case GUIsID.IRON_BARREL:
                return GUIbarrel.GUI.buildGUI(IronBarrelType.values()[ID], player.inventory, (TileEntityIronBarrel) te);
            case GUIsID.GOLD_BARREL:
                return GUIbarrel.GUI.buildGUI(IronBarrelType.values()[ID], player.inventory, (TileEntityGoldBarrel) te);
            case GUIsID.DIAMOND_BARREL:
                return GUIbarrel.GUI.buildGUI(IronBarrelType.values()[ID], player.inventory, (TileEntityDiamondBarrel) te);
            case GUIsID.OBSIDIAN_BARREL:
                return GUIbarrel.GUI.buildGUI(IronBarrelType.values()[ID], player.inventory, (TileEntityObsidianBarrel) te);
            default:
                return null;
        }
    }
}