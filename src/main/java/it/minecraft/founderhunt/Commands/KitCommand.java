package it.minecraft.founderhunt.Commands;

import it.minecraft.founderhunt.FounderHunt;
import it.minecraft.founderhunt.Utils.Config;
import it.minecraft.founderhunt.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.tecnocraft.utils.utils.SubCommandFramework;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class KitCommand extends SubCommandFramework {
    public KitCommand(String label, String... aliases) {
        super(FounderHunt.get(), label, aliases);
    }

    @Override
    public void noArgs(CommandSender commandSender) {
        sendHelp(commandSender);
    }

    @SubCommand("setkit")
    @SubCommandMinArgs(1)
    @SubCommandUsage("<type>")
    @SubCommandPermission("founderhunt.kit.setkit")
    @SubCommandDescription("Imposta il kit da dare allo spawnpoint.")
    public void kitSetkit(CommandSender sender, String label, String[] args) {

        Player player = Validator.getPlayerSender(sender);
        String type = args[0].toLowerCase();

        if(!Arrays.asList("normal", "vip").contains(type)) {
            sender.sendMessage(ChatColor.RED + "Il tipo del kit può essere normal o vip.");
            return;
        }

        Config.KITS.set("kit." + type, Utils.invSerialize(player.getInventory()));
        Config.KITS.save();

        sender.sendMessage(ChatColor.GREEN + "Hai impostato correttamente l'inventario " + type + "!");

    }

}
