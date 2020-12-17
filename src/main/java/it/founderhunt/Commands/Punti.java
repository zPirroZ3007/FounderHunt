package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.database.Database;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Punti extends CommandFramework {
    public Punti() {
        super(FounderHunt.inst(), "punti");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        Player player = Validator.getPlayerSender(sender);
        if (args.length == 0)
            Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () -> {
                player.sendMessage("");
                try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM stats WHERE username=?")) {
                    ps.setString(1, player.getName());
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    player.sendMessage(String.format(" §3I tuoi punti: §b%s punti", rs.getInt("points")));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                player.sendMessage("");
            });
        else {
            Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () -> {
                try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM stats WHERE username=?")) {
                    ps.setString(1, args[0]);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        Messenger.sendErrorMessage(player, "Questo giocatore non esiste!");
                        return;
                    }
                    player.sendMessage("");
                    player.sendMessage(String.format(" §3Punti di %s: §b%s punti", args[0], rs.getInt("points")));
                    player.sendMessage("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}
