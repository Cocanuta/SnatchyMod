package com.snatchybuckles.snatchymod.blocks;

import com.snatchybuckles.snatchymod.tiles.TileMineController;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class BlockMineController extends Block implements ITileEntityProvider
{
    // Here we declare our BlockStates for this block, much like the colours on Wool, one block
    // can have many states.

    // Our BlockState is an int which will hold an ID to let us know what sort of ores should
    // be found in this mine. We can have 16 different mine types.

    // Now we creat the main method for our Block which declares all of the main information about our Block.
    public BlockMineController(MineType type)
    {
        super(Material.IRON); // The material modifies the sound effects the block makes when placed and walked on.
        setRegistryName("minecontroller_" + type.toString()); // The unique name within SnatchyMod that identifies our block.
        setUnlocalizedName(getRegistryName().toString()); // Used for localization (en_US.lang).
        setBlockUnbreakable(); // Stops the block being broken using tools (like BedRock);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS); // Set which tab it can be found on in creative mode.
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName()); // Register the Block as an Item.
        GameRegistry.registerTileEntity(TileMineController.class, getRegistryName().toString()); // Register our Tile Entity.
    }

    // Our initializer for our Block Model, which is called in our ClientProxy.
    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    // Now we make instantiate our TileEntity for this block. The TileEntity will allow us to update the block
    // every Tick to track each mines individual countdown to reset.
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileMineController();
    }

    // Here we make our own method to get our Tile Entity from this block.
    public TileMineController getTE(IBlockAccess world, BlockPos pos)
    {
        return(TileMineController)world.getTileEntity(pos);
    }

    // Here we Override the onBlockActivated method from the Block class to make it do what we need it to do.
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        // We check to see if the world is remote.
        if(!worldIn.isRemote)
        {
            // Grab the remaining ticks from our TileEntity counter and send the player a message containing it.
            playerIn.addChatMessage(new TextComponentString(getTE(worldIn, pos).getTimer() / 20 + " seconds till mine refresh."));
        }
        return true;
    }

    public enum MineType
    {
        iron, gold, clay
    }
}
