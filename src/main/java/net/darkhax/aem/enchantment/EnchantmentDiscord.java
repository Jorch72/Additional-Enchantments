package net.darkhax.aem.enchantment;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.aem.handler.ConfigurationHandler;
import net.darkhax.aem.util.Utilities;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentDiscord extends EnchantmentBase {
    
    public static boolean isEnabled = true;
    public static int id = 3602;
    public static int weight = 5;
    public static int maxLevel = 3;
    public static float odds = 1f;
    
    public EnchantmentDiscord() {
        
        super(id, weight, "discord", maxLevel, EnumEnchantmentType.weapon);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onDamageDealth (LivingHurtEvent event) {
        
        if (isValidUser(event.source.getEntity())) {
            
            EntityLivingBase user = (EntityLivingBase) event.source.getEntity();
            
            if (Utilities.tryPercentage((float) level(user.getHeldItem()) * odds))
                event.entityLiving.addPotionEffect(new PotionEffect(Utilities.getRandomPotionEffect().id, 200, 1));
        }
    }
    
    public static void syncConfig (Configuration cfg) {
        
        String name = "discord";
        
        isEnabled = ConfigurationHandler.isEnchantmentEnabled(name, isEnabled);
        id = ConfigurationHandler.getEnchantmentID(name, id);
        weight = ConfigurationHandler.getEnchantmentWeight(name, weight);
        maxLevel = ConfigurationHandler.getEnchantmentLevel(name, maxLevel);
    }
}