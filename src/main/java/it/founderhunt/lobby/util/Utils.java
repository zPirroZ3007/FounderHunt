package it.founderhunt.lobby.util;

import it.founderhunt.lobby.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

public class Utils {

    public static void sendToServer(Player player, String targetServer) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
    }

    public static int getBungeeOnlineCount(Player player) {
        return Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%bungee_total%"));
    }

    public static int getBungeeOnlineCountIn(Player player, String server) {
        return Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%bungee_" + server + "%"));
    }

    public static ItemStack randomGlassColor() {
        return new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) new Random().nextInt(18));
    }
}
