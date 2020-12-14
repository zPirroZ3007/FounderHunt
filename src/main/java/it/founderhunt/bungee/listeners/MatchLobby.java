package it.founderhunt.bungee.listeners;

import it.founderhunt.bungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.List;

public class MatchLobby implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerConnect(ServerConnectEvent event) {
        event.setTarget(getHub());
    }

    public static Listener to() {
        return new MatchLobby();
    }

    private ServerInfo getHub() {
        int x = 1;
        List<ServerInfo> servers = new ArrayList<>();
        while (true) {
            if (ProxyServer.getInstance().getServerInfo("lobby-" + x) == null)
                break;
            servers.add(ProxyServer.getInstance().getServerInfo("lobby-" + x));
            x += 1;
        }
        ServerInfo lobby = null;
        for (ServerInfo server : servers)
            lobby = lobby == null ? server : lobby.getPlayers().size() > server.getPlayers().size() ? server : lobby;
        return lobby;
    }
}
