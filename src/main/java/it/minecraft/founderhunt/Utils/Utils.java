package it.minecraft.founderhunt.Utils;

import com.google.common.collect.Lists;
import it.minecraft.founderhunt.FounderHunt;
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
import java.util.Set;

public class Utils {

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
        return "/tp " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + " " + location.getYaw() + " " + location.getPitch();
    }

    public static <T> T getOne(Set<T> set) {
        List<T> list = Lists.newArrayList();
        list.addAll(set);

        return list.get(Util.randNumber(0, set.size() - 1));
    }

    public static String invSerialize(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Inventory invDeserialize(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException | IOException e) {
            FounderHunt.get().getLogger().severe("Impossibile deserializzare l'inventario nella path " + data + ".");
            e.printStackTrace();
            return null;
        }
    }

}
