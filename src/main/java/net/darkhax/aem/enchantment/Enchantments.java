package net.darkhax.aem.enchantment;

import java.util.ArrayList;

public class Enchantments {
    
    public static ArrayList<EnchantmentBase> enchantmentList = new ArrayList<EnchantmentBase>();
    
    public static EnchantmentUpdraft updraft;
    public static EnchantmentCloaked cloaked;
    public static EnchantmentDiscord discord;
    
    public Enchantments() {
        
        if (updraft.isEnabled)
            updraft = new EnchantmentUpdraft();
            
        if (cloaked.isEnabled)
            cloaked = new EnchantmentCloaked();
            
        if (discord.isEnabled)
            discord = new EnchantmentDiscord();
    }
}
