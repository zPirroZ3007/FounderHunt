package it.minecraft.founderhunt.Listeners;

import it.minecraft.founderhunt.FounderHunt;
import it.minecraft.founderhunt.Objects.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        Player killed = FounderHunt.PLAYERS.get(event.getEntity().getName());

        if(event.getEntity().getKiller() != null) {
            Player killer = FounderHunt.PLAYERS.get(event.getEntity().getKiller().getName());

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + String.format("%s è stato ucciso da %s.", killed.getName(), killer.getName()));

            killed.remPoint();
            killer.addPoint();
            killer.addKill();
        }

        event.getDrops().clear();
        event.setCancelled(true);

        killed.teleportSpawnpoint();

    }

}
