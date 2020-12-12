package it.founderhunt.lobby;

import it.founderhunt.lobby.listeners.PreventListener;
import lombok.Getter;
import net.tecnocraft.utils.utils.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Listeners.register(getInstance(), PreventListener.to());
    }

    @Override
    public void onDisable() {

    }

}
