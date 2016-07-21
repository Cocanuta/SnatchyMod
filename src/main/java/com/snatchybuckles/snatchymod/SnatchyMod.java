package com.snatchybuckles.snatchymod;

import com.snatchybuckles.snatchymod.blocks.SnatchyBlocks;
import com.snatchybuckles.snatchymod.events.SnatchyEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

// This tells Forge Mod Loader that this class is the mod entry point.
@Mod(
        modid = SnatchyMod.MOD_ID,
        name = SnatchyMod.MOD_NAME,
        version = SnatchyMod.VERSION
)
// The main mod class.
public class SnatchyMod {

    // This is where we declare our mod information that is used in the @Mod annotation,
    // and can be accessed from elsewhere in the mod.
    public static final String MOD_ID = "snatchymod";
    public static final String MOD_NAME = "SnatchyMod";
    public static final String VERSION = "0.0.1";

    // Here we annotate our SidedProxy. In previous versions of Forge you would have to declare our ClientProxy
    // and ServerProxy manually, but now it will automatically look for them as nested classes within this
    // parent class.
    @SidedProxy
    public static CommonProxy proxy;

    @Mod.Instance
    public static SnatchyMod instance = new SnatchyMod();

    // Here we declare our logger which allows us to output log messages to the console and log files.
    public static Logger logger;

    // Here we create an EventHandler which listens for the PreInitialization of the mod.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Set our logger to be the ModLogger from the Init event.
        logger = event.getModLog();
        // Since we have nothing to pre-initialize, we'll simply log a message to say the mod is starting up.
        // This will show in the console as [snatchymod] Starting...
        logger.info("Starting...");

        // For each Initialization event we'll also call our proxy initialization methods.
        this.proxy.preInit(event);
    }

    // Here we create another EventHandler, this time for our main Initialization event.
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        this.proxy.init(event);
    }

    // Our final EventHandler for now, this time for our postInitialization event.
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        this.proxy.postInit(event);

        // We'll send another message to our logger to show that the mod finished starting up successfully.
        logger.info("Started Successfully!");
    }

    // Here we declare our nested Proxy classes. First our CommonProxy, and then our Client and Server proxies
    // which estend the CommonProxy.
    public static class CommonProxy
    {
        public void preInit(FMLPreInitializationEvent event)
        {
            // Here we call our initialization method from our Blocks class, this will initialize our blocks
            // on both Client and Server.
            SnatchyBlocks.init();
        }

        public void init(FMLInitializationEvent event)
        {

        }

        public void postInit(FMLPostInitializationEvent event)
        {

        }
    }

    public static class ClientProxy extends CommonProxy
    {
        // We're going to Override the preInit method of the CommonProxy to initialize our own methods just for
        // the Client.
        @Override
        public void preInit(FMLPreInitializationEvent event)
        {
            super.preInit(event);

            // Here we call our model init method from our Blocks class, this initialize our block models
            // on Client as it's not needed for the Server.
            SnatchyBlocks.initModels();
        }
    }

    public static class ServerProxy extends CommonProxy
    {
        // Just as we did for the Client, we're going to Override the CommonProxy's init method to call some
        // events just for the Server.
        @Override
        public void init(FMLInitializationEvent event)
        {
            super.init(event);

            // Here we register our EventHandler class on the Forge EVENT_BUS so it listens to any relevant events.
            MinecraftForge.EVENT_BUS.register(new SnatchyEventHandler());
        }
    }


}
