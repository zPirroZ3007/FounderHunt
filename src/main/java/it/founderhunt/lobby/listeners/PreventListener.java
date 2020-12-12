package it.founderhunt.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PreventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith("/"))
            return;
        event.setCancelled(true);
    }

    public static PreventListener to() {
        return new PreventListener();
    }

}
