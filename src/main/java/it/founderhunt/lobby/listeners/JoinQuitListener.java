package it.founderhunt.lobby.listeners;

import com.connorlinfoot.titleapi.TitleAPI;
import it.founderhunt.lobby.objects.LobbyPlayer;
import it.founderhunt.lobby.util.Icons;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        LobbyPlayer player = LobbyPlayer.to(event.getPlayer());
        event.setJoinMessage(null);
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.hidePlayer(player);
            player.hidePlayer(p);
        }

        player.addPotionEffects(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        player.setGameMode(GameMode.CREATIVE);

        TitleAPI.sendTitle(player, 3, 60, 3, "§c§lFounderHunt - Benvenuto!", "§7Seleziona una partita per iniziare");

        player.getInventory().clear();
        player.getInventory().setItem(4, Icons.SELECTOR);
        player.getInventory().setHeldItemSlot(4);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    public static Listener to() {
        return new JoinQuitListener();
    }

}
