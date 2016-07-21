package com.snatchybuckles.snatchymod.blocks;

/**
 * Created by cocan on 21/07/2016.
 */
public class SnatchyBlocks {
    // This is where we declare all of our blocks.
    public static BlockMineController blockMineController_Iron;
    public static BlockMineController blockMineController_Gold;
    public static BlockMineController blockMineController_Clay;

    // This is our init method that is called in the CommonProxy to initialize all of our blocks.
    public static void init()
    {
        blockMineController_Iron = new BlockMineController(BlockMineController.MineType.iron);
        blockMineController_Gold = new BlockMineController(BlockMineController.MineType.gold);
        blockMineController_Clay = new BlockMineController(BlockMineController.MineType.clay);
    }

    // This is our initModels method that is called in our ClientProxy to initialize all our block models
    // only for the client.
    public static void initModels()
    {
        blockMineController_Iron.initModel();
        blockMineController_Gold.initModel();
        blockMineController_Clay.initModel();
    }
}