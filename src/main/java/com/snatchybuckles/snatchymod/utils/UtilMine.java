package com.snatchybuckles.snatchymod.utils;

import com.snatchybuckles.snatchymod.blocks.BlockMineController;
import com.snatchybuckles.snatchymod.blocks.SnatchyBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by cocan on 21/07/2016.
 */
public class UtilMine {

    public static void createMine(World worldIn, BlockPos pos, EnumFacing direction)
    {
        int startX = pos.getX();
        int startY = pos.getY();
        int startZ = pos.getZ();
        BlockPos endPos = pos.offset(direction, 20).offset(direction.rotateY(), 20).offset(EnumFacing.DOWN, 20);
        int endX = endPos.getX();
        int endY = endPos.getY();
        int endZ = endPos.getZ();

        if(direction == EnumFacing.NORTH)
        {
            startZ = pos.getZ() - 20;
            endZ = endPos.getZ() + 20;
        }

        if(direction == EnumFacing.SOUTH)
        {
            startX = pos.getX() - 20;
            endX = endPos.getX() + 20;
        }

        if(direction == EnumFacing.WEST)
        {
            startX = pos.getX() - 20;
            startZ = pos.getZ() - 20;
            endX = endPos.getX() + 20;
            endZ = endPos.getZ() + 20;
        }

        for(int x = startX; x <= endX; x++)
        {
            for(int y = endY; y <= startY; y++)
            {
                for(int z = startZ; z <= endZ; z++)
                {
                    if(y == endY || x == startX || x == endX || z == startZ || z == endZ)
                    {
                        if(x == pos.getX() && y == pos.getY() && z == pos.getZ())
                        {

                        }
                        else
                        {
                            worldIn.setBlockState(new BlockPos(x, y, z), SnatchyBlocks.blockMineFrame.getDefaultState());
                        }
                    }
                    else
                    {
                        worldIn.setBlockToAir(new BlockPos(x, y, z));
                    }
                }
            }
        }

    }

    public static void fillMine(World worldIn, BlockPos startPos, EnumFacing direction, Block ore)
    {
        BlockPos pos = startPos.offset(direction).offset(direction.rotateY());
        int startX = pos.getX();
        int startY = pos.getY();
        int startZ = pos.getZ();
        BlockPos endPos = pos.offset(direction, 18).offset(direction.rotateY(), 18).offset(EnumFacing.DOWN, 19);
        int endX = endPos.getX();
        int endY = endPos.getY();
        int endZ = endPos.getZ();

        if(direction == EnumFacing.NORTH)
        {
            startZ = pos.getZ() - 18;
            endZ = endPos.getZ() + 18;
        }

        if(direction == EnumFacing.SOUTH)
        {
            startX = pos.getX() - 18;
            endX = endPos.getX() + 18;
        }

        if(direction == EnumFacing.WEST)
        {
            startX = pos.getX() - 18;
            startZ = pos.getZ() - 18;
            endX = endPos.getX() + 18;
            endZ = endPos.getZ() + 18;
        }

        for(int x = startX; x <= endX; x++)
        {
            for(int y = endY; y <= startY; y++)
            {
                for(int z = startZ; z <= endZ; z++)
                {
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(100) + 1;
                    Block blockToPlace = Blocks.STONE;
                    if(randomNumber <= 10)
                    {
                        blockToPlace = ore;
                    }
                    else
                    {
                        blockToPlace = Blocks.STONE;
                    }
                    worldIn.setBlockState(new BlockPos(x, y, z), blockToPlace.getDefaultState());
                }
            }
        }
    }

    public static void movePlayers(World worldIn, BlockPos pos, EnumFacing direction)
    {
        int startX = pos.getX();
        int startY = pos.getY();
        int startZ = pos.getZ();
        BlockPos endPos = pos.offset(direction, 20).offset(direction.rotateY(), 20).offset(EnumFacing.DOWN, 20);
        int endX = endPos.getX();
        int endY = endPos.getY();
        int endZ = endPos.getZ();

        if(direction == EnumFacing.NORTH)
        {
            startZ = pos.getZ() - 20;
            endZ = endPos.getZ() + 20;
        }

        if(direction == EnumFacing.SOUTH)
        {
            startX = pos.getX() - 20;
            endX = endPos.getX() + 20;
        }

        if(direction == EnumFacing.WEST)
        {
            startX = pos.getX() - 20;
            startZ = pos.getZ() - 20;
            endX = endPos.getX() + 20;
            endZ = endPos.getZ() + 20;
        }

        for(int x = startX; x <= endX; x++)
        {
            for(int y = endY; y <= startY; y++)
            {
                for(int z = startZ; z <= endZ; z++)
                {
                    EntityPlayer player = null;
                    player = worldIn.getClosestPlayer(x, y, z, 1, false);
                    if(player != null)
                        player.setPositionAndUpdate(x, startY + 1, z);
                }
            }
        }

    }
}
