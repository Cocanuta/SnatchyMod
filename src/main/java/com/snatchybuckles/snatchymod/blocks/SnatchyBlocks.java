package com.snatchybuckles.snatchymod.blocks;

import net.minecraft.init.Blocks;

public class SnatchyBlocks {
    // This is where we declare all of our blocks.
    public static BlockMineController blockMineController_iron;
    public static BlockMineController blockMineController_gold;
    public static BlockMineController blockMineController_clay;

    public static BlockMineFrame blockMineFrame;

    // This is our init method that is called in the CommonProxy to initialize all of our blocks.
    public static void init()
    {
        blockMineController_iron = new BlockMineController(BlockMineController.MineType.IRON);
        blockMineController_gold = new BlockMineController(BlockMineController.MineType.GOLD);
        blockMineController_clay = new BlockMineController(BlockMineController.MineType.CLAY);

        blockMineFrame = new BlockMineFrame();
    }

    // This is our initModels method that is called in our ClientProxy to initialize all our block models
    // only for the client.
    public static void initModels()
    {
        blockMineController_iron.initModel();
        blockMineController_gold.initModel();
        blockMineController_clay.initModel();

        blockMineFrame.initModel();
    }
}