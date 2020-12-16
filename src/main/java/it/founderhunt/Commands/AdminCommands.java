package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.SubCommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands extends SubCommandFramework {

    public AdminCommands() {
        super(FounderHunt.inst(), "admin");
    }

    @Override
    public void noArgs(CommandSender sender) {
        Validator.Permission(sender, Perms.ADMIN);
        Validator.getPlayerSender(sender);
        this.sendHelp(sender);
    }

    @SubCommand("setmode")
    @SubCommandDescription("Configura la modalità di gioco fra: RISCALDAMENTO, COMPETITIVO")
    @SubCommandMinArgs(1)
    @SubCommandUsage("<modalità>")
    @SubCommandPermission(Perms.ADMIN)
    @SuppressWarnings("unused")
    public void setMode(CommandSender sender, String label, String[] args) {

        GameModes gameMode = GameModes.to(args[0]);
        Utils.setMode(gameMode);

        for (Player player : Bukkit.getOnlinePlayers()) {

            if(Utils.getMode().equals(GameModes.COMPETITIVO))
                player.sendTitle("§c§lCOMPETIZIONE!", "§7Adesso le uccisioni verranno contate!", 10, 60 , 10);
            else if (Utils.getMode().equals(GameModes.RISCALDAMENTO))
                player.sendTitle("§e§lRISCALDAMENTO!", "§7I punti non verranno assegnati per le uccisioni", 10, 60, 10);

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.2F);

        }

        Messenger.sendSuccessMessage(sender, "Modalità di gioco impostata su " + gameMode.name() + "!");
    }
}
