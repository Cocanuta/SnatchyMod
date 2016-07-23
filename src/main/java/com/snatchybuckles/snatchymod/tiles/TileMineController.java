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
    private int timer = 400;
    private int interval = 400;
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
                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString("Mine resetting."));
                UtilMine.movePlayers(worldObj, pos, worldObj.getBlockState(pos).getValue(BlockMineController.FACING));
                UtilMine.fillMine(worldObj, pos, worldObj.getBlockState(pos).getValue(BlockMineController.FACING), mineType.getBlock());
                timer = interval;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        timer = compound.getInteger("timer");
        interval = compound.getInteger("interval");
        mineType = BlockMineController.MineType.values()[compound.getInteger("oreType")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("timer", timer);
        compound.setInteger("interval", interval);
        compound.setInteger("oreType", mineType.getId());
        return compound;
    }
}
