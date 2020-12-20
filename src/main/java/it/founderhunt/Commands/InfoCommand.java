package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.FHPlayer;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.command.CommandSender;

public class InfoCommand extends CommandFramework {
    public InfoCommand() {
        super(FounderHunt.inst(), "stats");
    }

    @Override
    public void execute(CommandSender commandSender, String s, String[] strings) {
        FHPlayer player = FHPlayer.to(Validator.getPlayerSender(commandSender));

        player.getPlayer().sendMessage("");
        player.getPlayer().sendMessage("  §6§lLe tue statistiche:");
        player.getPlayer().sendMessage("   §8▪ §3Punti: §b" + player.getPoints());
        player.getPlayer().sendMessage("   §8▪ §3Uccisioni: §b" + player.getPlayerKilled());
        player.getPlayer().sendMessage("   §8▪ §3Assists: §b" + player.getPlayerAssistKilled());
        player.getPlayer().sendMessage("   §8▪ §3Morti: §b" + player.getDeaths());
        player.getPlayer().sendMessage("");
    }
}
