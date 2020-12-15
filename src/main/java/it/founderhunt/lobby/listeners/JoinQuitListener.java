package it.founderhunt.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p == event.getPlayer())
                continue;

            p.hidePlayer(event.getPlayer());
            event.getPlayer().hidePlayer(p);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    public static Listener to() {
        return new JoinQuitListener();
    }

}
