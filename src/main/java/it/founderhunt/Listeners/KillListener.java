package it.minecraft.founderhunt.Listeners;

import it.minecraft.founderhunt.Objects.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        Player killed = Player.to(event.getEntity());
        Player killer = Player.to(event.getEntity().getKiller());

        if(killer == null)
            return;

        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + String.format("%s è stato ucciso da %s.", killed.getName(), killer.getName()));
        killed.remPoint();
        killer.addPoint();

    }

}
