package it.founderhunt.lobby.listeners;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AuthMeListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(LoginEvent event) {
        event.getPlayer().setGameMode(GameMode.CREATIVE);
    }

    public static Listener to() {
        return new AuthMeListener();
    }
}
