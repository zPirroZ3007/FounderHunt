package it.founderhunt.Listeners;

import it.founderhunt.Objects.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player.to(event.getPlayer()).teleportSpawnpoint();

    }

}
