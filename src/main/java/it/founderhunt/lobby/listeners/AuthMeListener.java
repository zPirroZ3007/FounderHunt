package it.founderhunt.lobby.listeners;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.xephi.authme.events.LoginEvent;
import it.founderhunt.lobby.util.Icons;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AuthMeListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.CREATIVE);
        TitleAPI.sendTitle(player, 3, 60, 3, "§c§lFounderHunt - Benvenuto!", "§7Seleziona una partita per iniziare");

        player.getInventory().clear();
        player.getInventory().setItem(4, Icons.SELECTOR);
        player.getInventory().setHeldItemSlot(4);
    }

    public static Listener to() {
        return new AuthMeListener();
    }
}
