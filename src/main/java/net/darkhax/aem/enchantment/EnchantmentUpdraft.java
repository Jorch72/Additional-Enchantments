package net.darkhax.aem.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class EnchantmentUpdraft extends EnchantmentBase {
    
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
                    event.player.worldObj.spawnParticle("snowshovel", event.player.posX + (rand.nextDouble() - 0.7D) * (double) event.player.width, (event.player.posY - 0.4) + rand.nextDouble() * (double) event.player.height - (double) event.player.yOffset, event.player.posZ + (rand.nextDouble() - 0.7D) * (double) event.player.width, Math.random(), Math.random(), Math.random());
            }
        }
    }
    
    public static void handleConfiguration (Configuration cfg) {
    
        String name = "updraft";
        
        id = cfg.getInt(name + "Id", "enchantment_" + name, id, 0, 4096, "The enchantment id for the " + name + " enchantment.");
        weight = cfg.getInt(name + "Weight", "enchantment_" + name, weight, 0, 100, "The weighting for the " + name + " enchantment.");
        maxLevel = cfg.getInt(name + "MaxLevel", "enchantment_" + name, maxLevel, 1, 9999, "The maximum level for the " + name + " enchantment.");
        
        minimumFall = cfg.getInt(name + "MinimumFall", "enchantment_" + name, minimumFall, 0, 50, "The distance the player must fall, in order for the enchantment to take effect.");
        spawnParticles = cfg.getBoolean(name + "SpawnParticles", "enchantment_" + name, spawnParticles, "Should the updraft effect cause particles to spawn?");
    }
}