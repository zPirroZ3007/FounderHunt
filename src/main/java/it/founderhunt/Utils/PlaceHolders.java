package it.founderhunt.Utils;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import it.founderhunt.FounderHunt;
import org.bukkit.Bukkit;

public class PlaceHolders {

    public static void register() {
        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "points", event -> String.valueOf(Stats.MAP.get(event.getPlayer().getName()).getPoints()));

        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "kills", event -> String.valueOf(Stats.MAP.get(event.getPlayer().getName()).getKills()));

        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "numonline", event -> String.valueOf(Bukkit.getOnlinePlayers().size()));

        PlaceholderAPI.registerPlaceholder(FounderHunt.inst(), "booster", event -> Utils.BOOSTER ? "On" : "Off");
    }

}
