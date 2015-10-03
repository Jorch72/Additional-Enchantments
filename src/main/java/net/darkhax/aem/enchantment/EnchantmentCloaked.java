package net.darkhax.aem.enchantment;

import net.darkhax.aem.handler.ConfigurationHandler;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.config.Configuration;

public class EnchantmentCloaked extends EnchantmentBase {
    
    public static boolean isEnabled = true;
    public static int id = 3601;
    public static int weight = 5;
    public static int maxLevel = 1;
    
    public EnchantmentCloaked() {
        
        super(id, weight, "cloaked", maxLevel, EnumEnchantmentType.armor);
    }
    
    public static void syncConfig (Configuration cfg) {
        
        String name = "cloaked";
        
        isEnabled = ConfigurationHandler.isEnchantmentEnabled(name, isEnabled);
        id = ConfigurationHandler.getEnchantmentID(name, id);
        weight = ConfigurationHandler.getEnchantmentWeight(name, weight);
        maxLevel = ConfigurationHandler.getEnchantmentLevel(name, maxLevel);
    }
}
