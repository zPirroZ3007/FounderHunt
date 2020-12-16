package it.founderhunt.Listeners;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.Player;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        Player killed = FounderHunt.PLAYERS.get(event.getEntity().getName());

        if (event.getEntity().getKiller() != null) {
            Player killer = FounderHunt.PLAYERS.get(event.getEntity().getKiller().getName());

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + String.format("%s è stato ucciso da %s.", killed.getName(), killer.getName()));

            if (Utils.getMode() != GameModes.RISCALDAMENTO) {
                if (isAssist(killed.getName())) {
                    for (String p : AssistKillHandler.ASSISTS.get(killed.getName()))
                        FounderHunt.PLAYERS.get(p).addAssistPoint();
                } else {
                    killer.addPoint();
                    killer.addKill();
                }
            }
        }

        event.getDrops().clear();
        event.setCancelled(true);

        killed.teleportSpawnpoint();

    }

    public boolean isAssist(String username) {
        if (!AssistKillHandler.ASSISTS.containsKey(username))
            return false;

        return AssistKillHandler.ASSISTS.get(username).size() > 1;
    }

}
