package it.founderhunt;

import com.google.common.collect.Maps;
import it.founderhunt.Commands.AdminCommands;
import it.founderhunt.Commands.InfoCommand;
import it.founderhunt.Commands.KitCommand;
import it.founderhunt.Commands.SpawnpointCommand;
import it.founderhunt.Listeners.JoinQuitListener;
import it.founderhunt.Listeners.KillListener;
import it.founderhunt.Listeners.PreventListener;
import it.founderhunt.Objects.Player;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.PlaceHolders;
import it.founderhunt.Utils.Stats;
import net.tecnocraft.utils.utils.Listeners;
import net.tecnocraft.utils.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class FounderHunt extends JavaPlugin {

    private static FounderHunt instance;
    public static HashMap<String, Player> PLAYERS = Maps.newHashMap();

    @Override
    public void onEnable() {
        instance = this;

        Config.initalize();
        registerCommands();
        registerEvents();
        Stats.loadAll();
        PlaceHolders.register();

        if (!Config.SPAWN.getSections().isEmpty())
            Bukkit.getOnlinePlayers().forEach(player -> {
                Player FHPlayer = Player.to(player);
                PLAYERS.putIfAbsent(player.getName(), FHPlayer);
                FHPlayer.teleportSpawnpoint();
            });

        Log.onEnable(this);
    }

    @Override
    public void onDisable() {
        PLAYERS.clear();
        Log.onDisable(this);
    }

    public static FounderHunt inst() {
        return instance;
    }

    private void registerCommands() {
        new KitCommand("founderhuntkit", "fhkit");
        new SpawnpointCommand("founderhuntspawnpoint", "fhspawnpoint", "fhsp");
        new InfoCommand(this, "info");
        new AdminCommands();
    }

    private void registerEvents() {
        Listeners.register(this, new JoinQuitListener(), new KillListener(), new PreventListener());
    }

}
