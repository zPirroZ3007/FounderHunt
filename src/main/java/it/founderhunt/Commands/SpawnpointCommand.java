package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.tecnocraft.utils.utils.SubCommandFramework;
import net.tecnocraft.utils.utils.TextComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("unused")
public class SpawnpointCommand extends SubCommandFramework {
    public SpawnpointCommand(String label, String... aliases) {
        super(FounderHunt.inst(), label, aliases);
    }

    @Override
    public void noArgs(CommandSender commandSender) {
        sendHelp(commandSender);
    }

    @SubCommand("add")
    @SubCommandPermission("founderhunt.spawnpoint.add")
    @SubCommandDescription("Imposta uno spawnpoint nel punto in cui ti trovi.")
    public void SpawnpointAdd(CommandSender sender, String label, String[] args) {

        Player player = Validator.getPlayerSender(sender);
        String[] sections = Arrays.copyOf(Config.SPAWN.getSections().toArray(), Config.SPAWN.getSections().toArray().length, String[].class);
        int number = 1;

        if(sections.length > 0) {
            number = Validator.getInteger(sections[sections.length - 1].replace("POINT-", "")) + 1;
        }

        Config.SPAWN.setLocation("POINT-" + number, Utils.roundedLocation(player.getLocation()));
        Config.SPAWN.save();

        player.sendMessage(ChatColor.GREEN + "Hai impostato correttamente lo spawnpoint #" + number + "!");

    }

    @SubCommand("remove")
    @SubCommandMinArgs(1)
    @SubCommandUsage("<id>")
    @SubCommandPermission("founderhunt.spawnpoint.remove")
    @SubCommandDescription("Rimuovi uno spawnpoint con un determinato ID")
    public void SpawnpointRemove(CommandSender sender, String label, String[] args) {

        int number = Validator.getInteger(args[0]);

        if(Config.SPAWN.getLocation("POINT-" + number) == null) {
            sender.sendMessage(ChatColor.RED + "Lo spawnpoint #" + number + " non esiste.");
            return;
        }

        Config.SPAWN.set("POINT-" + number, null);
        Config.SPAWN.save();

        sender.sendMessage(ChatColor.GREEN + "Hai rimosso correttamente lo spawnpoint #" + number + "!");

    }

    @SubCommand("list")
    @SubCommandPermission("founderhunt.spawnpoint.list")
    @SubCommandDescription("Visualizza la lista degli spawnpoint")
    public void SpawnpointList(CommandSender sender, String label, String[] args) {

        Player player = Validator.getPlayerSender(sender);

        if(Config.SPAWN.getSections().isEmpty()) {
            player.sendMessage(ChatColor.RED + "Non è stato impostato nessuno spawnpoint.");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§6Lista degli §lspawnpoint§r§6 impostati:");
        Config.SPAWN.getSections().forEach(section -> {
            Location location = Config.SPAWN.getLocation(section);
            assert location != null;
            player.sendMessage (
                    new TextComponent(ChatColor.DARK_GRAY + " » "),
                    new TextComponent(ChatColor.WHITE + Utils.getCoords(location, true) + " "),
                    new TextComponentBuilder()
                            .setText("[TP]")
                            .setColor(ChatColor.GRAY)
                            .setHover("§7Clicca per teletrasportarti.")
                            .setCommand(Utils.coordCommand(location))
                            .build()
            );
        });
        player.sendMessage("");

    }

}
