package it.founderhunt.lobby;

import it.founderhunt.lobby.listeners.AuthMeListener;
import it.founderhunt.lobby.listeners.JoinQuitListener;
import it.founderhunt.lobby.listeners.PreventListener;
import it.founderhunt.lobby.objects.LobbyPlayer;
import it.founderhunt.lobby.selector.Selector;
import it.founderhunt.lobby.util.Utils;
import lombok.Getter;
import net.tecnocraft.utils.utils.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Listeners.register(getInstance(),
                PreventListener.to(),
                JoinQuitListener.to(),
                AuthMeListener.to(),
                Selector.to());

        Main.getInstance().getServer().getMessenger()
                .registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");

        Bukkit.getScheduler().runTaskTimer(getInstance(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                LobbyPlayer player = LobbyPlayer.to(onlinePlayer);
                player.sendActionBarMessage(String.format("§c§lFounderHunt §8| §aGiocatori online:§r §a§l%s §8| §7mc.FounderHunt.it", Utils.getBungeeOnlineCount(player)));
            }
        }, 10, 10);
    }

    @Override
    public void onDisable() {

    }

}
