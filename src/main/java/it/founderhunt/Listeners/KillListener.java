package it.founderhunt.Listeners;

import com.google.common.collect.Sets;
import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.Player;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.chat.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;
import java.util.Set;

public class KillListener implements Listener {

    public static Set<String> SPAWNKILL = Sets.newHashSet();
    public static Set<String> SPAWNED = Sets.newHashSet();

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        Player killed = FounderHunt.PLAYERS.get(event.getEntity().getName());

        if (event.getEntity().getKiller() != null) {
            Player killer = FounderHunt.PLAYERS.get(event.getEntity().getKiller().getName());

            int points = 100;
            if (Bukkit.getPlayerExact(killed.getName()).hasPermission(Perms.FOUNDER))
                points = 10000;
            if(points != 100)
                killer.addPoint(points);

            if (isAssist(killed.getName())) {
                StringBuilder sb = new StringBuilder();
                boolean start = false;
                for (String p : AssistKillHandler.ASSISTS.get(killed.getName())) {
                    if (!p.equals(killed.getName()))
                        if (Utils.getMode() != GameModes.RISCALDAMENTO) {
                            if (!(killed.hasPermission(Perms.FOUNDER))) {
                                FounderHunt.PLAYERS.get(p).addAssistPoint();
                                FounderHunt.PLAYERS.get(p).addAssistKill();
                            }
                            killed.addDeath();
                        }
                    if (!start)
                        sb.append(p);
                    else
                        sb.append(" + ").append(p);
                    start = true;
                }

                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.DARK_GRAY + String.format("%s hanno ucciso %s.", sb.toString(), killed.getName())));
            } else {

                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.DARK_GRAY + String.format("%s è stato ucciso da %s.", killed.getName(), killer.getName())));

                if (!killed.getName().equals(killer.getName()))
                    if (Utils.getMode() != GameModes.RISCALDAMENTO) {
                        if (!(killed.hasPermission(Perms.FOUNDER))) {
                            killer.addPoint();
                            killer.addKill();
                        }
                        killed.addDeath();
                    }
            }
        }

        AssistKillHandler.ASSISTS.remove(killed.getName());
        event.getDrops().clear();
    }

    public boolean isAssist(String username) {
        if (!AssistKillHandler.ASSISTS.containsKey(username))
            return false;

        return AssistKillHandler.ASSISTS.get(username).size() > 1;
    }

    private void removeSpawnKillTimer(String username) {
        SPAWNKILL.remove(username);

        org.bukkit.entity.Player p = Bukkit.getPlayerExact(username);
        if (p != null)
            Messenger.sendWarnMessage(p, "Adesso puoi colpire ed essere colpito!");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player killed = FounderHunt.PLAYERS.get(event.getPlayer().getName());
        killed.clearInventoryFully();

        event.setRespawnLocation(Objects.requireNonNull(Config.SPAWN.getLocation(Utils.getOne(Config.SPAWN.getSections()))));

        SPAWNKILL.add(killed.getName());
        SPAWNED.add(killed.getName());
        killed.setFoodLevel(20);
        killed.getActivePotionEffects().forEach(p -> killed.removePotionEffect(p.getType()));

        Messenger.sendWarnMessage(killed, "Sei in invincibilità temporanea! Non puoi colpire né essere colpito");

        Bukkit.getScheduler().runTaskLater(FounderHunt.inst(), () -> removeSpawnKillTimer(killed.getName()), 200);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void giveKit(PlayerMoveEvent event) {
        if (SPAWNED.contains(event.getPlayer().getName())) {
            if (SPAWNKILL.contains(event.getPlayer().getName()))
                return;
            Player killed = FounderHunt.PLAYERS.get(event.getPlayer().getName());
            SPAWNED.remove(killed.getName());
            killed.teleportSpawnpoint();
        }

    }

}
