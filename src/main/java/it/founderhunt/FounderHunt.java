package it.minecraft.founderhunt;

import it.minecraft.founderhunt.Commands.KitCommand;
import it.minecraft.founderhunt.Commands.SpawnpointCommand;
import it.minecraft.founderhunt.Listeners.JoinQuitListener;
import it.minecraft.founderhunt.Utils.Config;
import it.minecraft.founderhunt.Utils.Stats;
import net.tecnocraft.utils.utils.Listeners;
import net.tecnocraft.utils.utils.Log;
import org.bukkit.plugin.java.JavaPlugin;

public final class FounderHunt extends JavaPlugin {

    private static FounderHunt instance;

    @Override
    public void onEnable() {
        instance = this;

        Config.initalize();
        new KitCommand("founderhuntkit", "fhkit");
        new SpawnpointCommand("founderhuntspawnpoint", "fhspawnpoint", "fhsp");
        Listeners.register(this, new JoinQuitListener());

        Stats.loadAll();

        Log.onEnable(this);
    }

    @Override
    public void onDisable() {
        Log.onDisable(this);
    }


    public static FounderHunt get() {
        return instance;
    }

}
