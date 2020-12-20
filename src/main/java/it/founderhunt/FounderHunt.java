package it.founderhunt;

import it.founderhunt.Commands.*;
import it.founderhunt.Listeners.AssistKillHandler;
import it.founderhunt.Listeners.JoinQuitListener;
import it.founderhunt.Listeners.KillListener;
import it.founderhunt.Listeners.PreventListener;
import it.founderhunt.Objects.FHPlayer;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.PlaceHolders;
import it.founderhunt.Utils.Stats;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.Listeners;
import net.tecnocraft.utils.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FounderHunt extends JavaPlugin {

    private static FounderHunt instance;

    @Override
    public void onEnable() {
        instance = this;

        Config.initalize();
        registerCommands();
        registerEvents();
        Stats.loadAll();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, Stats::loadAll, 40, 40);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Bukkit.getOnlinePlayers().forEach(player -> Messenger.sendWarnActionBarMessage(player, "§eSblocca vantaggi su: §6https://store.FounderHunt.it")), 20, 20);

        PlaceHolders.register();

        if (!Config.SPAWN.getSections().isEmpty())
            Bukkit.getOnlinePlayers().forEach(player -> {
                FHPlayer fhPlayer = FHPlayer.to(player);
                fhPlayer.teleportSpawnpoint();
            });

        Log.onEnable(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        Log.onDisable(this);
    }

    public static FounderHunt inst() {
        return instance;
    }

    private void registerCommands() {
        new KitCommand("founderhuntkit", "fhkit");
        new SpawnpointCommand("founderhuntspawnpoint", "fhspawnpoint", "fhsp");
        new InfoCommand();
        new AdminCommands();
        new Punti();
        new Classifica();
        new Booster();
    }

    private void registerEvents() {
        Listeners.register(this, new JoinQuitListener(), new KillListener(), new PreventListener(), new AssistKillHandler());
    }

}
