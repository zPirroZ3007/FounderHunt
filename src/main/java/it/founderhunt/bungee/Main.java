package it.founderhunt.bungee;

import it.founderhunt.bungee.commands.GameTitle;
import it.founderhunt.bungee.commands.Reload;
import it.founderhunt.bungee.listeners.MatchLobby;
import it.founderhunt.bungee.listeners.Motd;
import it.founderhunt.bungee.util.Util;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.tecnocraft.utils.utils.listeners.bungee.Listeners;

import java.io.File;
import java.nio.file.Files;

public class Main extends Plugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        load();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        Reload.to();
        GameTitle.to();
    }

    private void registerListeners() {
        Listeners.register(this, Motd.to(), MatchLobby.to());
    }

    private void load() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();

            File file = new File(Main.getInstance().getDataFolder(), "motd.txt");
            if (!file.exists())
                file.createNewFile();
            Util.setMotd(new String(Files.readAllBytes(file.toPath())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
