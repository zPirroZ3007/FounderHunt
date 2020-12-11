package it.minecraft.founderhunt;

import com.google.common.collect.Maps;
import it.minecraft.founderhunt.Commands.InfoCommand;
import it.minecraft.founderhunt.Commands.KitCommand;
import it.minecraft.founderhunt.Commands.SpawnpointCommand;
import it.minecraft.founderhunt.Listeners.JoinQuitListener;
import it.minecraft.founderhunt.Listeners.KillListener;
import it.minecraft.founderhunt.Objects.Player;
import it.minecraft.founderhunt.Utils.Config;
import it.minecraft.founderhunt.Utils.PlaceHolders;
import it.minecraft.founderhunt.Utils.Stats;
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
    }

    private void registerEvents() {
        Listeners.register(this, new JoinQuitListener(), new KillListener());
    }

}
