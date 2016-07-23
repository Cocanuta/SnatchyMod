package com.snatchybuckles.snatchymod.tiles;

import com.snatchybuckles.snatchymod.blocks.BlockMineController;
import com.snatchybuckles.snatchymod.utils.UtilMine;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by cocan on 21/07/2016.
 */
public class TileMineController extends TileEntity implements ITickable {

    // Here we set our timer field, and our interval in which to set the timer to when it resets.
    private int timer = 600;
    private BlockMineController.MineType mineType = BlockMineController.MineType.IRON;

    // Here we create a public method to get the current time remaining on the timer.
    public int getTimer()
    {
        return timer;
    }

    public BlockMineController.MineType getOreType()
    {
        return mineType;
    }

    public void setOreType(BlockMineController.MineType type)
    {
        mineType = type;
    }

    // Here we Override the update method to perform our actions every tick.
    @Override
    public void update() {
        // Check to see if the world is remote or not.
        if(!worldObj.isRemote)
        {
            timer--;
            if(timer <= 0)
            {
                float blockCount = UtilMine.checkMine(worldObj, pos, worldObj.getBlockState(pos).getValue(BlockMineController.FACING));
                float minePercentage = (blockCount / 7220) * 100;
                if(minePercentage < 99.000f)
                {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString("Mine Percentage: " + minePercentage));
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString(blockCount + " blocks remaining."));
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString("Mine resetting."));
                    UtilMine.movePlayers(worldObj, pos, worldObj.getBlockState(pos).getValue(BlockMineController.FACING));
                    UtilMine.fillMine(worldObj, pos, worldObj.getBlockState(pos).getValue(BlockMineController.FACING), mineType.getBlock());
                }
                else
                {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString("Mine Percentage: " + minePercentage));
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString(blockCount + " blocks remaining."));
                }
                timer = 600;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        timer = compound.getInteger("timer");
        mineType = BlockMineController.MineType.values()[compound.getInteger("oreType")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("timer", timer);
        compound.setInteger("oreType", mineType.getId());
        return compound;
    }
}
