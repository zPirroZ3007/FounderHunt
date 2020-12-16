package it.founderhunt.Listeners;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.Player;
import it.founderhunt.Utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = Player.to(event.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () -> {
            Stats.loadPlayer(player.getName());
            FounderHunt.PLAYERS.put(event.getPlayer().getName(), Player.to(event.getPlayer()));
        });

        player.teleportSpawnpoint();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

}
