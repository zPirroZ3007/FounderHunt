package it.founderhunt.Utils;

import com.google.common.collect.Lists;
import it.founderhunt.FounderHunt;
import it.founderhunt.enums.GameModes;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.tecnocraft.utils.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {

    @Getter
    @Setter
    public static GameModes mode = GameModes.RISCALDAMENTO;

    public static boolean BOOSTER = false;
    public static String BOOSTING = "";

    public static Location roundedLocation(Location location) {
        Location roundedLocation = location.clone();
        roundedLocation.setPitch(0);
        if (Math.abs(roundedLocation.getYaw()) % 45.0f != 0.0f) {
            float yawNormalized = Math.round(roundedLocation.getYaw() / 45.0f) * 45.0f;
            roundedLocation.setYaw(yawNormalized);
        }

        roundedLocation.setX(Math.round(roundedLocation.getX() * 2.0) / 2.0);
        roundedLocation.setZ(Math.round(roundedLocation.getZ() * 2.0) / 2.0);
        return roundedLocation;
    }

    public static String getCoords(Location loc, boolean world) {
        if (world)
            return loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
        else
            return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
    }


    public static String coordCommand(Location location) {
        return "/tp " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
    }

    public static <T> T getOne(Set<T> set) {
        List<T> list = Lists.newArrayList();
        list.addAll(set);

        return list.get(new Random().nextInt(set.size()));
    }

}
