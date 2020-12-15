package it.founderhunt.bungee.commands;

import it.founderhunt.bungee.Main;
import it.founderhunt.bungee.util.Perms;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.tecnocraft.utils.chat.MessengerBungee;
import net.tecnocraft.utils.utils.BungeeCommandFramework;

public class GameTitle extends BungeeCommandFramework {

    public GameTitle() {
        super(Main.getInstance(), "gametitle");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(Perms.ADMIN))
            return;

        Validator.Condition(args.length == 0, String.format("§cUso corretto: §7/%s <messaggio>", label));
        StringBuilder sb = new StringBuilder();
        for (String arg : args)
            sb.append(arg).append(" ");

        for (ProxiedPlayer p : Main.getInstance().getProxy().getPlayers())
            if (p.getServer().getInfo().getName().startsWith("game-"))
                MessengerBungee.sendTitle(p, "§e§lAttenzione!", ChatColor.translateAlternateColorCodes('&', "§7" + sb.toString()));

        MessengerBungee.sendSuccessMessage(sender, "Title inviato con successo!");
    }

    public static BungeeCommandFramework to() {
        return new GameTitle();
    }
}
