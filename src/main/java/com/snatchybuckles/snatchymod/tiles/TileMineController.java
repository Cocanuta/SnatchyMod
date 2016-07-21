package com.snatchybuckles.snatchymod.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by cocan on 21/07/2016.
 */
public class TileMineController extends TileEntity implements ITickable {

    // Here we set our timer field, and our interval in which to set the timer to when it resets.
    private int timer = 4800;
    private int interval = 4800;

    // Here we create a public method to get the current time remaining on the timer.
    public int getTimer()
    {
        return timer;
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
                //TODO: fill Mine.
                timer = interval;
            }
        }
    }
}
