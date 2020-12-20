package it.founderhunt.Listeners;

import com.google.common.collect.Sets;
import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.FHPlayer;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.chat.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Set;

public class KillListener implements Listener {

    public static Set<String> SPAWNKILL = Sets.newHashSet();
    public static Set<String> SPAWNED = Sets.newHashSet();
    public static Set<String> Killstreaks = Sets.newHashSet();

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        FHPlayer killed = FHPlayer.to(event.getEntity());


        if (event.getEntity().getKiller() != null) {
            FHPlayer killer = FHPlayer.to(event.getEntity().getKiller());
            Utils.getLiveKills().put(killer.getName(), Utils.getLiveKills().getOrDefault(killer.getName(), 0) + 1);

            if (!Utils.isFounder(killer.getName()) && !Utils.isScorta(killer.getName()))
                switch (Utils.getLiveKills().get(killer.getName())) {
                    case 3:
                        killstreak1(killer);
                        break;
                    case 5:
                        killstreak2(killer);
                        break;
                    case 10:
                        killstreak3(killer);
                        break;
                }
            int points = 100;
            if (Utils.isFounder(killed.getPlayer().getName()))
                points = 10000;

            if (points != 100) {
                if (Utils.getMode() != GameModes.RISCALDAMENTO)
                    killer.addPoint(points);
                Utils.broadcastMessage(String.format("§6§l%s §eha ucciso il founder §6§l%s", killer.getName(), killed.getName()));
            } else if (isAssist(killed.getPlayer().getName())) {
                StringBuilder sb = new StringBuilder();
                boolean start = false;
                for (String p : AssistKillHandler.ASSISTS.get(killed.getPlayer().getName())) {
                    if (!p.equals(killed.getPlayer().getName()))
                        if (Utils.getMode() != GameModes.RISCALDAMENTO) {
                            FHPlayer.to(Bukkit.getPlayerExact(p)).addAssistPoint();
                            FHPlayer.to(Bukkit.getPlayerExact(p)).addAssistKill();
                            killed.addDeath();
                        }
                    if (!start)
                        sb.append(p);
                    else
                        sb.append(" + ").append(p);
                    start = true;
                }

                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.DARK_GRAY + String.format("§8%s §7hanno ucciso §8%s.", sb.toString(), killed.getName())));
            } else {

                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.DARK_GRAY + String.format("§8%s§7 è stato ucciso da §8%s.", killed.getName(), killer.getName())));

                if (!killed.getName().equals(killer.getName()))
                    if (Utils.getMode() != GameModes.RISCALDAMENTO) {
                        killer.addPoint();
                        killer.addKill();
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
        if (Utils.isFounder(username)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 600 * 20, 4));
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 127));
        }
        if (p != null)
            Messenger.sendWarnMessage(p, "Adesso puoi colpire ed essere colpito!");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FHPlayer killed = FHPlayer.to(event.getPlayer());
        killed.clearInventoryFully();

        if (!Killstreaks.contains(killed.getName()))
            Utils.getLiveKills().remove(killed.getName());
        else
            Killstreaks.remove(killed.getName());

        event.setRespawnLocation(Objects.requireNonNull(Config.SPAWN.getLocation(Utils.getOne(Config.SPAWN.getSections()))));

        SPAWNKILL.add(killed.getName());
        SPAWNED.add(killed.getName());
        killed.getPlayer().setFoodLevel(20);
        killed.getPlayer().getActivePotionEffects().forEach(p -> killed.getPlayer().removePotionEffect(p.getType()));

        if (Utils.isFounder(killed.getName()) || Utils.isScorta(killed.getName()))
            killed.getPlayer().setGameMode(GameMode.SPECTATOR);
        Messenger.sendWarnMessage(killed.getPlayer(), "Sei in invincibilità temporanea! Non puoi colpire né essere colpito");

        Bukkit.getScheduler().runTaskLater(FounderHunt.inst(), () -> removeSpawnKillTimer(killed.getName()), 200);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void giveKit(PlayerMoveEvent event) {
        if (SPAWNED.contains(event.getPlayer().getName())) {
            if (SPAWNKILL.contains(event.getPlayer().getName()))
                return;
            FHPlayer killed = FHPlayer.to(event.getPlayer());
            SPAWNED.remove(killed.getName());
            killed.teleportSpawnpoint();
        }

    }

    private void killstreak1(FHPlayer killer) {
        Killstreaks.add(killer.getName());
        killer.getPlayer().setHealth(0);
        Utils.broadcastMessage(String.format("§4%s §cha fatto una killstreak da 3!", killer.getName()));
    }

    private void killstreak2(FHPlayer killer) {
        Killstreaks.add(killer.getName());
        killer.getPlayer().setHealth(0);
        Utils.broadcastMessage(String.format("§4%s §cha fatto una killstreak da 5!", killer.getName()));
    }

    private void killstreak3(FHPlayer killer) {
        Killstreaks.add(killer.getName());
        killer.getPlayer().setHealth(0);
        Utils.broadcastMessage(String.format("§4%s §cha fatto una killstreak da 10!", killer.getName()));
    }


}
