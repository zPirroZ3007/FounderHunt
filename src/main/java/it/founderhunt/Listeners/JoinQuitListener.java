package it.founderhunt.Listeners;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.FHPlayer;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Stats;
import it.founderhunt.Utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        FHPlayer player = FHPlayer.to(event.getPlayer());

        if(Utils.isFounder(player.getName()))
            event.getPlayer().setGameMode(GameMode.SPECTATOR);

        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () -> Stats.loadPlayer(player.getName()));

        player.teleportSpawnpoint();
        player.getPlayer().teleport(Objects.requireNonNull(Config.SPAWN.getLocation(Utils.getOne(Config.SPAWN.getSections()))));
        if (Config.FOUNDERS.getStringList("founders").contains(player.getName())) {
            LuckPerms lp = LuckPermsProvider.get();
            User user = lp.getUserManager().getUser(player.getName());
            for (Node node : user.getNodes())
                if (node.getKey().equals(Perms.FOUNDER))
                    return;
            Node node = Node.builder(Perms.FOUNDER).value(true).build();
            Node node2 = Node.builder("essentials.gamemode.spectator").value(true).build();
            Node node3 = Node.builder("essentials.gamemode.survival").value(true).build();
            Node node4 = Node.builder("essentials.gamemode").value(true).build();
            user.data().add(node);
            user.data().add(node2);
            user.data().add(node3);
            user.data().add(node4);
            lp.getUserManager().saveUser(user);
        }

        if (Config.FOUNDERS.getStringList("scorta").contains(player.getName())) {
            LuckPerms lp = LuckPermsProvider.get();
            User user = lp.getUserManager().getUser(player.getName());
            for (Node node : user.getNodes())
                if (node.getKey().equals(Perms.SCORTA))
                    return;

            Node node = Node.builder(Perms.SCORTA).value(true).build();
            user.data().add(node);
            lp.getUserManager().saveUser(user);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

}
