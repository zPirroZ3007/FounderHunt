package it.minecraft.founderhunt.Utils;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import it.minecraft.founderhunt.FounderHunt;
import org.bukkit.Bukkit;

public class PlaceHolders {

    public static void register() {
        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "points", event -> {
            return String.valueOf(FounderHunt.PLAYERS.get(event.getPlayer().getName()).getPoints());
        });

        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "kills", event -> {
            return String.valueOf(FounderHunt.PLAYERS.get(event.getPlayer().getName()).getPlayerKilled());
        });

        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "numonline", event -> {
            return String.valueOf(Bukkit.getOnlinePlayers().size());
        });
    }

}
