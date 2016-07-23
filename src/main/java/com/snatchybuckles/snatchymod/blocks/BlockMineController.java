package com.snatchybuckles.snatchymod.blocks;

import com.snatchybuckles.snatchymod.tiles.TileMineController;
import com.snatchybuckles.snatchymod.utils.UtilMine;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMineController extends Block implements ITileEntityProvider
{
    // Here we declare our BlockStates for this block, much like the colours on Wool, one block
    // can have many states.
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    // Our BlockState is an int which will hold an ID to let us know what sort of ores should
    // be found in this mine. We can have 16 different mine types.

    // Now we creat the main method for our Block which declares all of the main information about our Block.
    public BlockMineController(MineType mineType)
    {
        super(Material.IRON);
        setRegistryName("minecontroller_" + mineType.getName());
        setUnlocalizedName(getRegistryName().toString());
        setBlockUnbreakable();
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TileMineController.class, getRegistryName().toString());
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
        getTE(worldIn, pos).setOreType(MineType.fromString(getRegistryName().toString().split("_")[1]));
        UtilMine.createMine(worldIn, pos, placer.getHorizontalFacing());
        UtilMine.fillMine(worldIn, pos, placer.getHorizontalFacing(), MineType.fromString(getRegistryName().toString().split("_")[1]).getBlock());
    }

    // Here we Override the onBlockActivated method from the Block class to make it do what we need it to do.
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        // We check to see if the world is remote.
        if(!worldIn.isRemote)
        {
            // Grab the remaining ticks from our TileEntity counter and send the player a message containing it.
            playerIn.addChatMessage(new TextComponentString(getTE(worldIn, pos).getTimer() / 20 + " seconds till mine refresh."));
            playerIn.addChatMessage(new TextComponentString("ORE TYPE: " + getTE(worldIn, pos).getOreType().getName()));
            playerIn.addChatMessage(new TextComponentString("FACING: " + state.getValue(FACING).toString()));
        }
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex()-2;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    public enum MineType implements IStringSerializable {
        IRON(0, "iron", Blocks.IRON_ORE),
        GOLD(1, "gold", Blocks.GOLD_ORE),
        CLAY(2, "clay", Blocks.CLAY);

        private final int id;
        private final String name;
        private final Block block;

        MineType(int id, String name, Block block)
        {
            this.id = id;
            this.name = name;
            this.block = block;
        }

        public int getId()
        {
            return id;
        }

        public Block getBlock()
        {
            return block;
        }

        @Override
        public String getName()
        {
            return name;
        }

        public static MineType fromString(String name)
        {
            if(name != null)
            {
                for(MineType mineType : MineType.values())
                {
                    if(name.equalsIgnoreCase(mineType.name()))
                    {
                        return mineType;
                    }
                }
            }
            return  null;
        }
    }



}
