package it.founderhunt.Objects;

import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Stats;
import it.founderhunt.Utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class Player extends CraftPlayer {

    @Getter
    private int points;

    @Getter @Setter
    private int playerKilled;

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
        sendTitle("§a§lSTART!", "§7Dai la caccia ai Founder e uccidi tutti!", 10, 60, 10);

    }

    public boolean isVIP() {
        return hasPermission("founderhunt.vip");
    }

    public void addPoint() {
        addPoint(100);
    }

    public void addPoint(int n) {
        setPoints(getPoints() + n);
        sendTitle("", "§6Hai ricevuto " + n + " " + (n == 1 ? "punto!" : "punti!"), 5, 30, 5);
        playSound(getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.3F);
    }

    public void remPoint() {
        remPoint(100);
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

    public void addKill() {
        setPlayerKilled(getPlayerKilled() + 1);
    }

}
