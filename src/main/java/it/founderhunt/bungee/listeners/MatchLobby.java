package it.founderhunt.bungee.listeners;

import it.founderhunt.bungee.util.Util;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void giveQueueBypass(ServerConnectEvent event) {
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(event.getPlayer().getName());
        for(Node node : user.getNodes())
            if(node.getKey().equals("bungeequeue.bypass"))
                return;
        if(!Util.getPriority().contains(event.getPlayer().getName()))
            return;
        Node node = Node.builder("bungeequeue.bypass").value(true).build();
        Node node2 = Node.builder("streamer").value(true).build();
        user.data().add(node);
        user.data().add(node2);
        api.getUserManager().saveUser(user);
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
