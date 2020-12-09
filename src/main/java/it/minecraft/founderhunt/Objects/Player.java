package it.minecraft.founderhunt.Objects;

import it.minecraft.founderhunt.Utils.Config;
import it.minecraft.founderhunt.Utils.Stats;
import it.minecraft.founderhunt.Utils.Utils;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class Player extends CraftPlayer {

    @Getter
    private int points;

    public Player (org.bukkit.entity.Player player) {
        super((CraftServer) player.getServer(), ((CraftPlayer) player).getHandle());
        setPoints(Stats.MAP.getOrDefault(player.getName(), 0));
    }

    public static Player to(org.bukkit.entity.Player player) {
        if(player == null) return null;
        return new Player(player);
    }

    public void clearInventoryFully() {
        this.setItemOnCursor(null);
        PlayerInventory playerInventory = this.getInventory();
        playerInventory.clear();
        playerInventory.setArmorContents(null);
        Inventory topOpenInventory = this.getOpenInventory().getTopInventory();
        if (topOpenInventory.getType() == InventoryType.CRAFTING) {
            topOpenInventory.clear();
        }
    }

    @SuppressWarnings("deprecation")
    public void giveKit() {

        if(isVIP()) {
            Config.KITS.getSections("kit.vip").forEach(slot -> getInventory().setItem(Integer.parseInt(slot), Config.KITS.getItemStack("kit.vip." + slot)));
        } else {
            Config.KITS.getSections("kit.normal").forEach(slot -> getInventory().setItem(Integer.parseInt(slot), Config.KITS.getItemStack("kit.normal." + slot)));
        }

        updateInventory();

    }

    public void teleportSpawnpoint() {

        this.clearInventoryFully();
        this.teleport(Config.SPAWN.getLocation(Utils.getOne(Config.SPAWN.getSections())));
        this.giveKit();

        playSound(getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.2F);
    }

    public boolean isVIP() {
        return hasPermission("founderhunt.vip");
    }

    public void addPoint() {
        addPoint(1);
    }

    public void addPoint(int n) {
        setPoints(getPoints() + n);
    }

    public void remPoint() {
        remPoint(1);
    }

    public void remPoint(int n) {
        if(getPoints() - n < 0)
            setPoints(0);

        setPoints(getPoints() - n);
    }

    public void setPoints(int points) {
        this.points = points;

        // TODO Aggiorna il valore nel database (in asincrono?)
    }

}
