package insanity.mods.ironbarrels.client;

import insanity.mods.ironbarrels.IronBarrelType;
import insanity.mods.ironbarrels.tileentities.TileEntityDiamondBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityGoldBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityIronBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityObsidianBarrel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIbarrel extends GuiContainer {
    private final GUI type;

    public GUIbarrel(GUI type, IInventory player, IInventory chest) {
        super(type.makeContainer(player, chest));
        this.type = type;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
    }

    public int getRowLength() {
        return type.mainType.getRowLength();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // new "bind tex"
        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    public enum ResourceList {
        IRON(new ResourceLocation("ironbarrels", "textures/gui/ironcontainer.png")),
        GOLD(new ResourceLocation("ironbarrels", "textures/gui/goldcontainer.png")),
        DIAMOND(new ResourceLocation("ironbarrels", "textures/gui/diamondcontainer.png"));
        public final ResourceLocation location;

        ResourceList(ResourceLocation loc) {
            this.location = loc;
        }
    }

    public enum GUI {
        IRON(184, 202, ResourceList.IRON, IronBarrelType.IRON),
        GOLD(184, 256, ResourceList.GOLD, IronBarrelType.GOLD),
        DIAMOND(238, 256, ResourceList.DIAMOND, IronBarrelType.DIAMOND),
        OBSIDIAN(238, 256, ResourceList.DIAMOND, IronBarrelType.OBSIDIAN);

        private final int xSize;
        private final int ySize;
        private final ResourceList guiResourceList;
        private final IronBarrelType mainType;

        GUI(int xSize, int ySize, ResourceList guiResourceList, IronBarrelType mainType) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.guiResourceList = guiResourceList;
            this.mainType = mainType;

        }

        public static GUIbarrel buildGUI(IronBarrelType type, IInventory playerInventory, TileEntityIronBarrel chestInventory) {
            return new GUIbarrel(values()[TileEntityIronBarrel.getType().ordinal()], playerInventory, chestInventory);
        }

        public static GUIbarrel buildGUI(IronBarrelType type, IInventory playerInventory, TileEntityGoldBarrel chestInventory) {
            return new GUIbarrel(values()[TileEntityGoldBarrel.getType().ordinal()], playerInventory, chestInventory);
        }

        public static GUIbarrel buildGUI(IronBarrelType type, IInventory playerInventory, TileEntityDiamondBarrel chestInventory) {
            return new GUIbarrel(values()[TileEntityDiamondBarrel.getType().ordinal()], playerInventory, chestInventory);
        }

        public static GUIbarrel buildGUI(IronBarrelType type, IInventory playerInventory, TileEntityObsidianBarrel chestInventory) {
            return new GUIbarrel(values()[TileEntityObsidianBarrel.getType().ordinal()], playerInventory, chestInventory);
        }

        private Container makeContainer(IInventory player, IInventory chest) {
            return new ContainerIronBarrel(player, chest, mainType, xSize, ySize);
        }
    }
}
