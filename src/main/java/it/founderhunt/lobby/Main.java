package it.founderhunt.lobby;

import it.founderhunt.lobby.listeners.AuthMeListener;
import it.founderhunt.lobby.listeners.JoinQuitListener;
import it.founderhunt.lobby.listeners.PreventListener;
import it.founderhunt.lobby.selector.Selector;
import lombok.Getter;
import net.tecnocraft.utils.utils.Listeners;
import net.tecnocraft.utils.utils.fileconfigurations.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Getter
    private static ConfigFile databaseConf;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists())
            getDataFolder().mkdir();
        databaseConf = ConfigFile.initialize("database", Main.getInstance());

        Listeners.register(getInstance(),
                PreventListener.to(),
                JoinQuitListener.to(),
                AuthMeListener.to(),
                Selector.to());

        Main.getInstance().getServer().getMessenger()
                .registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
    }

    @Override
    public void onDisable() {

    }

}
