package net.darkhax.aem;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.darkhax.aem.common.ProxyCommon;
import net.darkhax.aem.handler.ConfigurationHandler;
import net.darkhax.aem.handler.EnchantmentListExpansionHandler;
import net.darkhax.aem.util.Constants;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
public class AdditionalEnchantments {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static AdditionalEnchantments instance;
    
    public static ConfigurationHandler cfg = null;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
        
        proxy.registerSidedEvents();
        cfg = new ConfigurationHandler(pre.getSuggestedConfigurationFile());
        new EnchantmentListExpansionHandler();
        
        cfg.syncConfigData();
    }
    
    /**
     * A basic method which is to be used when printing any debug or error messages. This
     * method will comply with the ConfigurationHandler setting for debug messages.
     * 
     * @param message: the message to print to the console.
     */
    public static void printDebugMessage (String message) {
        
        if (ConfigurationHandler.allowDebug)
            Constants.LOGGER.info(message);
    }
}