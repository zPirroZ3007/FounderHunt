package it.founderhunt.Commands;

import it.founderhunt.FounderHunt;
import it.founderhunt.Objects.FHPlayer;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import it.founderhunt.enums.GameModes;
import net.tecnocraft.utils.utils.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Booster extends CommandFramework {
    public Booster() {
        super(FounderHunt.inst(), "booster");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Validator.Condition(Utils.BOOSTER, "La partita sta già venendo boostata!");
        Validator.Condition(Utils.getMode() == GameModes.RISCALDAMENTO, "La modalità riscaldamento è attiva!");

        Player p = Validator.getPlayerSender(sender);
        Validator.Permission(p, Perms.BOOST);
        FHPlayer player = FHPlayer.to(p);
        Validator.Condition(player.getBoosting(), "Non puoi boostare di nuovo una partita!");

        Utils.BOOSTER = true;
        Utils.BOOSTING = player.getName();
        Bukkit.getScheduler().scheduleSyncDelayedTask(FounderHunt.inst(), this::stopBoosting, 20 * 600);
        player.setBoosting(true);

        Bukkit.getServer().getOnlinePlayers().forEach(pf -> {
            pf.sendMessage("");
            pf.sendMessage(String.format("§5%s §dsta boostando la tua partita! Per 10 minuti ci saranno §51.5x§d punti in più!",
                    player.getName()));
            pf.sendMessage("");
        });

    }

    private void stopBoosting() {
        Utils.BOOSTER = false;
        Utils.BOOSTING = "";
    }
}
