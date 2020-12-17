package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.Player;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoCommand extends CommandFramework {
    public InfoCommand(JavaPlugin plugin, String label, String... aliases) {
        super(plugin, label, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String s, String[] strings) {
        Player player = FounderHunt.PLAYERS.get(commandSender.getName());

        if(strings.length != 0) {
            player.addPoint();
            return;
        }

        player.sendMessage("");
        player.sendMessage("  §6§lLe tue statistiche:");
        player.sendMessage("   §8▪ §3Punti: §b" + player.getPoints());
        player.sendMessage("   §8▪ §3Uccisioni: §b" + player.getPlayerKilled());
        player.sendMessage("   §8▪ §3Assists: §b" + player.getPlayerAssistKilled());
        player.sendMessage("   §8▪ §3Morti: §b" + player.getDeaths());
        player.sendMessage("");
    }
}
