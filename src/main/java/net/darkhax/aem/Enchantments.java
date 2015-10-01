package net.darkhax.aem;

import java.util.ArrayList;

import net.darkhax.aem.enchantment.EnchantmentBase;
import net.darkhax.aem.enchantment.EnchantmentUpdraft;
import net.minecraft.enchantment.Enchantment;

public class Enchantments {
    
    public static ArrayList<EnchantmentBase> enchantmentList = new ArrayList<EnchantmentBase>();
    
    public static Enchantment updraft = new EnchantmentUpdraft();
    
    public Enchantments() {
    
    }
}
