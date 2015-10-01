package net.darkhax.aem.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Utilities {
    
    /**
     * Creates a MovingObjectPosition which represents the look vectors of a player.
     * 
     * @param player: The instance of the player which is being checked.
     * @param length: The distance for this to go. Larger value means further away from the
     *            player.
     * @return MovingObjectPosition: A MovingObjectPosition which represents the look vector of
     *         a player.
     */
    public static MovingObjectPosition rayTrace (EntityPlayer player, double length) {
    
        Vec3 vecPlayer = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 vecLook = player.getLookVec();
        Vec3 vecMax = vecPlayer.addVector(vecLook.xCoord * length, vecLook.yCoord * length, vecLook.zCoord * length);
        return player.worldObj.rayTraceBlocks(vecPlayer, vecMax);
    }
    
    /**
     * Prepares an ItemStack to have its nbt worked with.
     * 
     * @param stack: Instance of an ItemStack that is about to have nbt work done on it.
     * @return ItemStack: The same ItemStack provided however with an NBTTagCompound if one did
     *         not exist previously.
     */
    public static ItemStack prepareStackWithNBT (ItemStack stack) {
    
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        
        return stack;
    }
    
    /**
     * This method allows for the player to break blocks in a ring based way.
     * 
     * @param world: Instance of the World.
     * @param player: Instance of the player breaking the block.
     * @param materials: An arry of materials that this effect applies to.
     * @param layers: How far out this effect should reach. The block being broken is 0 layers,
     *            by increasing this number above 0 extra rings will be broken. Example: 1
     *            breaks a 3x3 area.
     */
    
    /**
     * Attempts to break blocks in a ring around where the player is looking. For example, a
     * 3x3 area, or a 5x5 area.
     * 
     * @param world: An instance of the world in which these blocks are being broken.
     * @param player: The player who is going to attempt breaking these blocks.
     * @param materials: An array of Material which can be broken by this attempt.
     * @param layers: How many layers should this spread outwards. For example, if the 1 is
     *            used, blocks will be broken in 1 block outwards in every direction, this
     *            creates a 3x3 breakage.
     */
    public static void attemptAOEBreak (World world, EntityPlayer player, Material[] materials, int layers) {
    
        ItemStack stack = prepareStackWithNBT(player.getHeldItem());
        MovingObjectPosition objPos = rayTrace(player, 4.5d);
        if (objPos != null && player instanceof EntityPlayerMP && stack != null) {
            
            int x = objPos.blockX;
            int y = objPos.blockY;
            int z = objPos.blockZ;
            EntityPlayerMP playermp = (EntityPlayerMP) player;
            NBTTagCompound tags = stack.stackTagCompound;
            
            if (!tags.hasKey("breaking") || !tags.getBoolean("breaking")) {
                
                tags.setBoolean("breaking", true);
                int rangeX = layers;
                int rangeY = layers;
                int rangeZ = layers;
                
                switch (objPos.sideHit) {
                    case 0:
                        
                    case 1:
                        rangeY = 0;
                        break;
                    
                    case 2:
                        
                    case 3:
                        rangeZ = 0;
                        break;
                    
                    case 4:
                        
                    case 5:
                        rangeX = 0;
                        break;
                }
                
                for (int posX = x - rangeX; posX <= x + rangeX; posX++) {
                    
                    for (int posY = y - rangeY; posY <= y + rangeY; posY++) {
                        
                        for (int posZ = z - rangeZ; posZ <= z + rangeZ; posZ++) {
                            
                            Block block = world.getBlock(posX, posY, posZ);
                            
                            for (Material mat : materials) {
                                
                                if (block != null && mat == block.getMaterial() && block.getPlayerRelativeBlockHardness(playermp, world, x, posY, z) > 0)
                                    playermp.theItemInWorldManager.tryHarvestBlock(posX, posY, posZ);
                            }
                        }
                    }
                }
                
                tags.setBoolean("breaking", false);
            }
        }
    }
    
    /**
     * A method which handles the calculating of percentages. While this isn't a particularly
     * difficult piece of code, it has been added for the sake of simplicity.
     * 
     * @param percent: The percent chance that this method should return true. 1.00 = 100% 0.00
     *            = 0%
     * @return boolean: Returns are randomly true or false, based on the suplied percentage.
     */
    public boolean tryPercentage (double percent) {
    
        return Math.random() < percent;
    }
}