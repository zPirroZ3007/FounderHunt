package it.minecraft.founderhunt.Listeners;

import it.minecraft.founderhunt.FounderHunt;
import it.minecraft.founderhunt.Objects.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = Player.to(event.getPlayer());
        FounderHunt.PLAYERS.putIfAbsent(event.getPlayer().getName(), player);

        player.teleportSpawnpoint();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        FounderHunt.PLAYERS.remove(event.getPlayer().getName());

    }

}
