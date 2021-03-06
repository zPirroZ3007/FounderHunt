package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Config;
import net.md_5.bungee.api.ChatColor;
import net.tecnocraft.utils.utils.SubCommandFramework;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitCommand extends SubCommandFramework {
    public KitCommand(String label, String... aliases) {
        super(FounderHunt.inst(), label, aliases);
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

        if(!Arrays.asList("normal", "vip", "scorta", "founder", "killstreak1", "killstreak2", "killstreak3").contains(type)) {
            sender.sendMessage(ChatColor.RED + "Il tipo del kit può essere normal, scorta, founder, killstreak1-2-3 o vip.");
            return;
        }

        for(int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack item = player.getInventory().getContents()[i];
            if(item == null) continue;
            Config.KITS.set("kit." + type + "." + i, item);
        }

        Config.KITS.save();

        sender.sendMessage(ChatColor.GREEN + "Hai impostato correttamente l'inventario " + type + "!");

    }

}
