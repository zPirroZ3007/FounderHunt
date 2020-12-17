package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.database.Database;
import net.tecnocraft.utils.chat.Messenger;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Classifica extends CommandFramework {

    public Classifica() {
        super(FounderHunt.inst(), "classifica", "top");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        int page = args.length == 0 ? 1 : Validator.getInteger(args[0]);
        page = (page * 10) - 10;

        int finalPage = page;
        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () -> {

            try (PreparedStatement ps = Database.getConnection().prepareStatement(
                    String.format("SELECT * FROM stats ORDER BY `points` DESC LIMIT %s, 10;", finalPage))) {
                ResultSet rs = ps.executeQuery();
                Validator.notCondition(rs.next(), "Questa pagina non esiste!");
                rs.beforeFirst();

                sender.sendMessage("");
                sender.sendMessage(" §8§l§m-----§6§l Top 10 hunters §8§l§m-----");
                int i = finalPage;
                while (rs.next()) {
                    i++;
                    sender.sendMessage(String.format("   §8#%s §7%s §8- §e%s punti", i, rs.getString("username"), rs.getInt("points")));
                }
                sender.sendMessage("");

            } catch (ExecuteException ex) {
                Messenger.sendErrorMessage(sender, ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}
