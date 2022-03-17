package insanity.mods.ironbarrels;

import cpw.mods.fml.common.registry.GameRegistry;
import insanity.mods.ironbarrels.blocks.BlockDiamondBarrel;
import insanity.mods.ironbarrels.blocks.BlockGoldBarrel;
import insanity.mods.ironbarrels.blocks.BlockIronBarrel;
import insanity.mods.ironbarrels.blocks.BlockObsidianBarrel;
import net.minecraft.block.Block;

public class ModBlocks {

    public static final Block ironBarrel = new BlockIronBarrel();
    public static final Block goldBarrel = new BlockGoldBarrel();
    public static final Block diamondBarrel = new BlockDiamondBarrel();
    public static final Block obsidianBarrel = new BlockObsidianBarrel();

    public static void init() {
        GameRegistry.registerBlock(ironBarrel, "ironBarrel");
        GameRegistry.registerBlock(goldBarrel, "goldBarrel");
        GameRegistry.registerBlock(diamondBarrel, "diamondBarrel");
        GameRegistry.registerBlock(obsidianBarrel, "obsidianBarrel");
    }
}