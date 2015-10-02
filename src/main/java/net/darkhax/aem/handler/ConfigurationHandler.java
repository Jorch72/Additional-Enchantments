package net.darkhax.aem.handler;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.aem.enchantment.EnchantmentUpdraft;
import net.darkhax.aem.util.Constants;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
    
    public static Configuration config;
    
    public static boolean allowDebug = true;
    public static boolean expandEnchantmentId = true;
    
    public ConfigurationHandler(File configFile) {
        
        config = new Configuration(configFile);
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
        
        if (event.modID.equals(Constants.MOD_ID))
            syncConfigData();
    }
    
    public void syncConfigData () {
        
        config.getBoolean("allowDebug", "general", true, "If disabled, Additional Enchantments will not be able to print debug lines to the console");
        config.getBoolean("shouldExpand", "general", true, "If enabled, Additional Enchantments will try to expand the Enchantment Array from 256 ids to 4096 ids.");
        
        EnchantmentUpdraft.handleConfiguration(config);
        
        if (config.hasChanged())
            config.save();
    }
}