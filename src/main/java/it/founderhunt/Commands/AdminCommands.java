package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.CommandFramework;
import net.tecnocraft.utils.utils.SubCommandFramework;
import org.bukkit.command.CommandSender;

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
        GameModes mode;
        try {
            mode = GameModes.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CommandFramework.ExecuteException("Questo valore non esiste!");
        }

        Utils.setMode(mode);
        Messenger.sendSuccessMessage(sender, "Modalità di gioco impostata su: " + mode.name() + "!");
    }
}
