package com.snatchybuckles.snatchymod.events;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by cocan on 21/07/2016.
 */
public class SnatchyEventHandler {

    // Here we subscribe to the PlayerLoggedInEvent so we can send some messages to server and player when they login.
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        // Get the server player list and send everyone a message that a new player has joined.
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString(event.player.getDisplayNameString() + " joined."));

        // Send the joining player a few welcome messages.
        event.player.addChatMessage(new TextComponentString(TextFormatting.GOLD + "Welcome to the " + TextFormatting.GREEN.BOLD + "SnatchyBuckles" + TextFormatting.GOLD + " server " + event.player.getDisplayNameString() + "!"));
        event.player.addChatMessage(new TextComponentString(TextFormatting.DARK_GRAY + "There are currently " + FMLCommonHandler.instance().getMinecraftServerInstance().getCurrentPlayerCount() + " players online."));
    }

    //Here we subscribe to the PlayerLoggedOutEvent to inform the server players that a player left.
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        // Send the left message to all remaining players.
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(new TextComponentString(event.player.getDisplayNameString() + " left."));
    }
}
