package insanity.mods.ironbarrels;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import insanity.mods.ironbarrels.core.proxy.CommonProxy;
import insanity.mods.ironbarrels.lib.Reference;

import java.util.Locale;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
public class IronBarrels {
    public static final String TEXTURE_PATH = Reference.MOD_ID.toLowerCase(Locale.ENGLISH) + ":";
    @Instance("ironbarrels")
    public static IronBarrels instance;
    @SidedProxy(clientSide = "insanity.mods.ironbarrels.core.proxy.ClientProxy", serverSide = "insanity.mods.ironbarrels.core.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper networkWrapper;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerEntities();
        proxy.registerRenderers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent e) {
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
