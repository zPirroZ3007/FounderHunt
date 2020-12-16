package it.founderhunt.Commands;

import com.destroystokyo.paper.Title;
import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.CommandFramework;
import net.tecnocraft.utils.utils.SubCommandFramework;
import org.bukkit.Bukkit;
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
        GameModes mode;
        try {
            mode = GameModes.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CommandFramework.ExecuteException("Questo valore non esiste!");
        }

        Utils.setMode(mode);

        for (Player p : Bukkit.getOnlinePlayers()) {
            it.founderhunt.Objects.Player player = new it.founderhunt.Objects.Player(p);
            Title title = null;
            
            switch (Utils.getMode()) {
                case COMPETITIVO:
                    title = new Title("§c§lCOMPETIZIONE INIZIATA!", "§7Adesso le uccisioni verranno contate!", 10, 60 , 10);
                    break;
                case RISCALDAMENTO:
                    title = new Title("§e§lMODALITA' RISCALDAMENTO!", "§7I punti non verranno assegnati per le uccisioni", 10, 60, 10);
                    break;
            }
            
            player.sendTitle(title);
        }

        Messenger.sendSuccessMessage(sender, "Modalità di gioco impostata su: " + mode.name() + "!");
    }
}
