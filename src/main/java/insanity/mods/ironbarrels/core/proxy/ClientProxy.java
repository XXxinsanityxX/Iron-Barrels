package insanity.mods.ironbarrels.core.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import insanity.mods.ironbarrels.client.renderer.block.BlockBarrelRenderer;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        registerBlockRenderers();
    }

    private void registerBlockRenderers() {
        RenderingRegistry.registerBlockHandler(new BlockBarrelRenderer());
    }
}