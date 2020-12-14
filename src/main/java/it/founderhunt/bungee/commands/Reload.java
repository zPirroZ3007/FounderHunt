package it.founderhunt.bungee.commands;

import it.founderhunt.bungee.Main;
import it.founderhunt.bungee.listeners.Motd;
import it.founderhunt.bungee.util.Util;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.tecnocraft.utils.utils.BungeeCommandFramework;
import net.tecnocraft.utils.utils.TextComponentBuilder;

import java.io.File;
import java.nio.file.Files;

public class Reload extends BungeeCommandFramework {
    public Reload() {
        super(Main.getInstance(), "reload");
    }

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String s, String[] strings) {
        if ((sender instanceof ProxiedPlayer))
            return;

        File file = new File(Main.getInstance().getDataFolder(), "motd.txt");
        Util.setMotd(new String(Files.readAllBytes(file.toPath())));

        sender.sendMessage(new TextComponentBuilder().setText("Reload completato!").setColor(ChatColor.GREEN).build());
    }

    public static BungeeCommandFramework to() {
        return new Reload();
    }
}
