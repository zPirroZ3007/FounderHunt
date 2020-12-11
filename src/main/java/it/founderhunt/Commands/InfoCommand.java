package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.Player;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoCommand extends CommandFramework {
    public InfoCommand(JavaPlugin plugin, String label) {
        super(plugin, label);
    }

    @Override
    public void execute(CommandSender commandSender, String s, String[] strings) {
        Player player = FounderHunt.PLAYERS.get(commandSender.getName());

        if(strings.length != 0) {
            player.addPoint();
            return;
        }

        player.sendMessage("");
        player.sendMessage("Punti: " + player.getPoints());
        player.sendMessage("Player Killed: " + player.getPlayerKilled());
        player.sendMessage("");
    }
}
