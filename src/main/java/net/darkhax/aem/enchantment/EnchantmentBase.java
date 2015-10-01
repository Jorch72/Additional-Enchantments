package net.darkhax.aem.enchantment;

import java.util.Random;

import net.darkhax.aem.Enchantments;
import net.darkhax.aem.util.Constants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class EnchantmentBase extends Enchantment {
    
    private int maxLevel;
    protected Random rand = Constants.RANDOM;
    
    /**
     * Constructs an enchantment using the basic code. This constructor establishes the
     * fundamental properties for all enchantments added by this mod.
     * 
     * @param id: A numeric id for this enchantment. By default, this can only go up to 256 and
     *            can not over lap with any other ids. This mod has a built in component which
     *            raises the maximum value to 4096.
     * @param weight: The weighting of this enchantment.
     * @param unlocalizedName: The unlocalized name for this enchantment. aem. is appended to
     *            the beginning automatically.
     * @param maxLevel: The highest level possible for this enchantment.
     * @param type: The EnchantmentType for this enchantment. This determines what items may
     *            use this enchantment, along with the creative tabs this enchantment is placed
     *            in.
     */
    protected EnchantmentBase(int id, int weight, String unlocalizedName, int maxLevel, EnumEnchantmentType type) {
    
        super(id, weight, type);
        this.name = "aem." + unlocalizedName;
        this.maxLevel = maxLevel;
        Enchantments.enchantmentList.add(this);
    }
    
    @Override
    public int getMaxLevel () {
    
        return this.maxLevel;
    }
    
    @Override
    public int getMinEnchantability (int level) {
    
        return 10 + 20 * (level - 1);
    }
    
    @Override
    public int getMaxEnchantability (int level) {
    
        return super.getMinEnchantability(level) + 50;
    }
    
    /**
     * A simple method to check if an entity is a valid target for an effect.
     * 
     * @param entity: The entity to be checked.
     * @return boolean: True, if the entity is an instance of EntityLiving.
     */
    public boolean isValidTarget (Entity entity) {
    
        return (entity instanceof EntityLiving);
    }
    
    /**
     * A simple method to check if an entity is a valid user for an effect.
     * 
     * @param entity: The entity to be checked.
     * @return boolean: True, if the entity is an instance of EntityLivingBase, and is holding
     *         an item which has at least lv 1 of this effect.
     */
    public boolean isValidUser (Entity entity) {
    
        if (entity instanceof EntityLivingBase) {
            
            ItemStack heldStack = ((EntityLivingBase) entity).getHeldItem();
            
            if (heldStack != null && heldStack.getItem() != null && level(heldStack) > 0)
                return true;
        }
        
        return false;
    }
    
    /**
     * An object oriented way to check the level of an enchantment on an ItemStack, from the
     * enchantment's side of the code.
     * 
     * @param stack: The item beind used.
     * @return int: The level of the effect on the weapon.
     */
    public int level (ItemStack stack) {
    
        return EnchantmentHelper.getEnchantmentLevel(this.effectId, stack);
    }
    
    /**
     * This method is called every time the configuration file is being synchronized.
     * 
     * @param cfg: Instance of the Configuration for this mod.
     */
    public void onConfigSync (Configuration cfg) {
    
    }
}