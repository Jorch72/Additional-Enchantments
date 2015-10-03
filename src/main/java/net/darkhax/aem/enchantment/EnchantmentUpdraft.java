package net.darkhax.aem.enchantment;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.darkhax.aem.handler.ConfigurationHandler;
import net.darkhax.aem.util.Constants;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.config.Configuration;

public class EnchantmentUpdraft extends EnchantmentBase {
    
    public static boolean isEnabled = true;
    public static int id = 3600;
    public static int weight = 5;
    public static int maxLevel = 1;
    public static int minimumFall = 3;
    public static boolean spawnParticles = true;
    
    public EnchantmentUpdraft() {
        
        super(id, weight, "updraft", maxLevel, EnumEnchantmentType.armor_feet);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onPlayerUpdate (TickEvent.PlayerTickEvent event) {
        
        if (level(event.player.getCurrentArmor(0)) > 0 && event.player.fallDistance > minimumFall) {
            
            event.player.fallDistance = 0;
            
            if (event.player.worldObj.isRemote && spawnParticles) {
                for (int i = 0; i < 25; i++)
                    event.player.worldObj.spawnParticle("snowshovel", event.player.posX + (Constants.RANDOM.nextDouble() - 0.7D) * (double) event.player.width, (event.player.posY - 0.4) + Constants.RANDOM.nextDouble() * (double) event.player.height - (double) event.player.yOffset, event.player.posZ + (Constants.RANDOM.nextDouble() - 0.7D) * (double) event.player.width, Math.random(), Math.random(), Math.random());
            }
        }
    }
    
    public static void syncConfig (Configuration cfg) {
        
        String name = "updraft";
        
        isEnabled = ConfigurationHandler.isEnchantmentEnabled(name, isEnabled);
        id = ConfigurationHandler.getEnchantmentID(name, id);
        weight = ConfigurationHandler.getEnchantmentWeight(name, weight);
        maxLevel = ConfigurationHandler.getEnchantmentLevel(name, maxLevel);
        
        minimumFall = cfg.getInt(name + "MinimumFall", "enchantment " + name, minimumFall, 0, 50, "The distance the player must fall, in order for the enchantment to take effect.");
        spawnParticles = cfg.getBoolean(name + "SpawnParticles", "enchantment " + name, spawnParticles, "Should the updraft effect cause particles to spawn?");
    }
}