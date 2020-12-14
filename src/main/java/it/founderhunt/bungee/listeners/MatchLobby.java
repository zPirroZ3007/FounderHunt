package it.founderhunt.bungee.listeners;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchLobby implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerConnect(ServerConnectEvent event) {
        if (!getJoinList().contains(event.getPlayer().getName()))
            return;
        event.setTarget(getHub());
        getJoinList().remove(event.getPlayer().getName());
    }

    public static Listener to() {
        return new MatchLobby();
    }

    @Getter
    private static final Set<String> joinList = new HashSet<>();

    private ServerInfo getHub() {
        int x = 1;
        List<ServerInfo> servers = new ArrayList<>();
        while (ProxyServer.getInstance().getServerInfo("lobby-" + x) != null) {
            servers.add(ProxyServer.getInstance().getServerInfo("lobby-" + x));
            x += 1;
        }
        ServerInfo lobby = null;
        for (ServerInfo server : servers)
            lobby = lobby == null ? server : lobby.getPlayers().size() > server.getPlayers().size() ? server : lobby;
        return lobby;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(PreLoginEvent event) {
        getJoinList().add(event.getConnection().getName());
    }
}
