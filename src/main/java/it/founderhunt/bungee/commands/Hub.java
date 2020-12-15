package it.founderhunt.bungee.commands;

import it.founderhunt.bungee.Main;
import it.founderhunt.bungee.listeners.MatchLobby;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.tecnocraft.utils.utils.BungeeCommandFramework;

public class Hub extends BungeeCommandFramework {

    public Hub() {
        super(Main.getInstance(), "hub", null, "lobby");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        MatchLobby.getJoinList().add(player.getName());
        player.connect(Main.getInstance().getProxy().getServerInfo("lobby-1"));
    }

    public static BungeeCommandFramework to() {
        return new Hub();
    }
}
