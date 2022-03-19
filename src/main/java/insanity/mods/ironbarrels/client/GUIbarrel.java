package insanity.mods.ironbarrels.client;

import insanity.mods.ironbarrels.IronBarrelType;
import insanity.mods.ironbarrels.tileentities.TileEntityDiamondBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityGoldBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityIronBarrel;
import insanity.mods.ironbarrels.tileentities.TileEntityObsidianBarrel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIbarrel extends GuiContainer {
    private final GUI type;
    private final IInventory upperChestInventory;
    private final IInventory lowerChestInventory;

    public GUIbarrel(GUI type, IInventory player, IInventory chest) {
        super(type.makeContainer(player, chest));
        this.type = type;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
        this.upperChestInventory = player;
        this.lowerChestInventory = chest;
    }

    public int getRowLength() {
        return type.mainType.getRowLength();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        if (type.mainType == IronBarrelType.IRON) {
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        } else {
            drawTexturedRect(x, y, 0, 0, xSize, ySize, 256, 512, 1);
        }
    }

    public static void drawTexturedRect(double x, double y, int u, int v, int width, int height, int imageWidth, int imageHeight, double scale) {
        double minU = (double)u / (double)imageWidth;
        double maxU = (double)(u + width) / (double)imageWidth;
        double minV = (double)v / (double)imageHeight;
        double maxV = (double)(v + height) / (double)imageHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + scale*(double)width, y + scale*(double)height, 0, maxU, maxV);
        tessellator.addVertexWithUV(x + scale*(double)width, y, 0, maxU, minV);
        tessellator.addVertexWithUV(x, y, 0, minU, minV);
        tessellator.addVertexWithUV(x, y + scale*(double)height, 0, minU, maxV);
        tessellator.draw();
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(this.lowerChestInventory.hasCustomInventoryName() ? this.lowerChestInventory.getInventoryName() : I18n.format(this.lowerChestInventory.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.hasCustomInventoryName() ? this.upperChestInventory.getInventoryName() : I18n.format(this.upperChestInventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
    }

    public enum ResourceList {
        IRON(new ResourceLocation("ironbarrels", "textures/gui/iron.png")),
        GOLD(new ResourceLocation("ironbarrels", "textures/gui/gold.png")),
        DIAMOND(new ResourceLocation("ironbarrels", "textures/gui/diamond.png"));
        public final ResourceLocation location;

        ResourceList(ResourceLocation loc) {
            this.location = loc;
        }
    }

    public enum GUI {
        IRON(176, 222, ResourceList.IRON, IronBarrelType.IRON),
        GOLD(176, 276, ResourceList.GOLD, IronBarrelType.GOLD),
        DIAMOND(230, 276, ResourceList.DIAMOND, IronBarrelType.DIAMOND),
        OBSIDIAN(230, 276, ResourceList.DIAMOND, IronBarrelType.OBSIDIAN);

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
