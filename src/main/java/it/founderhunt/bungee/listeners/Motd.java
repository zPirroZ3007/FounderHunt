package it.founderhunt.bungee.listeners;

import it.founderhunt.bungee.util.Util;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Motd implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void motd(ProxyPingEvent event) {

        ServerPing response = event.getResponse();

        response.setDescriptionComponent(new TextComponent(Util.getMotd()));
        event.setResponse(response);

    }

    public static Listener to() {
        return new Motd();
    }

}
