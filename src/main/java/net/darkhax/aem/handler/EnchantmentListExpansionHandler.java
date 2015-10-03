package net.darkhax.aem.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.darkhax.aem.AdditionalEnchantments;
import net.minecraft.enchantment.Enchantment;

/**
 * When initialized, this class attempts to expand the Enchantment array which holds all of the
 * enchantments. By default, this array can only hold 256 entries. All available ids can easily
 * be used by one or two mods, that is why this class was created. If the initialization of
 * this class is successful, the Enchantment array will be capable of holding 4096 entries,
 * allowing for 16x more enchantments to be added to the game. Any mod can make use of these
 * new found ids, so if you're making a mod pack, you can configure other mods to use ids
 * outside of the 256 range. This code can be disabled in the configuration file, and will
 * automatically disable itself if the id list has already been expanded. If any issues arise
 * because of this class file, please let me know, and I will work to resolve the issue.
 */
public class EnchantmentListExpansionHandler {
    
    Field enchantmentsList = null;
    Field modifiers = null;
    
    public EnchantmentListExpansionHandler() {
        
        if (ConfigurationHandler.expandEnchantmentId && Enchantment.enchantmentsList.length < 4096) {
            
            AdditionalEnchantments.printDebugMessage("Attempting to expand the Enchantment Array.");
            
            try {
                
                enchantmentsList = ReflectionHelper.findField(Enchantment.class, "b", "field_77331_b", "enchantmentsList");
                modifiers = Field.class.getDeclaredField("modifiers");
                
                if (enchantmentsList != null) {
                    
                    modifiers.setAccessible(true);
                    modifiers.setInt(enchantmentsList, enchantmentsList.getModifiers() & ~Modifier.FINAL);
                    
                    Enchantment[] existing = (Enchantment[]) enchantmentsList.get(null);
                    Enchantment[] expanded = new Enchantment[4096];
                    System.arraycopy(existing, 0, expanded, 0, existing.length);
                    enchantmentsList.set(existing, expanded);
                    AdditionalEnchantments.printDebugMessage("The Enchantment Array has been expanded to allow 4096 entries.");
                    return;
                }
            }
            
            catch (Exception e) {
                
                AdditionalEnchantments.printDebugMessage("An exception occured during the expansion process. Please report this issue to Darkhax right away! Make sure to provide a copy of this log!");
                e.printStackTrace();
            }
            
            AdditionalEnchantments.printDebugMessage("The expansion of the Enchantment Array has been disabled. The array shall not be expanded.");
        }
    }
}
