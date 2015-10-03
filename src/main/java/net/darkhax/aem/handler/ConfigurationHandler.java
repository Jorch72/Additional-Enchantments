package net.darkhax.aem.handler;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.aem.enchantment.EnchantmentCloaked;
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
        
        EnchantmentUpdraft.syncConfig(config);
        EnchantmentCloaked.syncConfig(config);
        
        if (config.hasChanged())
            config.save();
    }
    
    public static boolean isEnchantmentEnabled (String enchName, boolean enabled) {
        
        return config.getBoolean(enchName + "Enabled", "enchantment " + enchName, true, "Checks if the " + enchName + " enchantment should be enabled. If set to false, this enchantment will not be available.");
    }
    
    public static int getEnchantmentID (String enchName, int id) {
        
        return config.getInt(enchName + "ID", "enchantment " + enchName, id, 0, 4096, "The enchantment id for the " + enchName + " enchantment.");
    }
    
    public static int getEnchantmentWeight (String enchName, int weight) {
        
        return config.getInt(enchName + "Weight", "enchantment " + enchName, weight, 0, 100, "The weighting for the " + enchName + " enchantment.");
    }
    
    public static int getEnchantmentLevel (String enchName, int maxLevel) {
        
        return config.getInt(enchName + "MaxLevel", "enchantment " + enchName, maxLevel, 1, 9999, "The maximum level for the " + enchName + " enchantment.");
    }
}