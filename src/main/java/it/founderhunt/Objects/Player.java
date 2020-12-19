package it.founderhunt.Objects;

import it.founderhunt.FounderHunt;
import it.founderhunt.Utils.Config;
import it.founderhunt.Utils.Stats;
import it.founderhunt.Utils.Utils;
import it.founderhunt.database.Database;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class Player extends CraftPlayer {

    @Getter
    private int points;

    @Getter
    @Setter

    private int playerKilled;
    @Getter
    @Setter
    private int playerAssistKilled;

    @Getter
    @Setter
    private int deaths;

    public Player(org.bukkit.entity.Player player) {
        super((CraftServer) player.getServer(), ((CraftPlayer) player).getHandle());
        this.points = Stats.MAP.getOrDefault(player.getName(), new Data(0, 0, 0, 0)).getPoints();
        this.playerKilled = Stats.MAP.getOrDefault(player.getName(), new Data(0, 0, 0, 0)).getKills();
        this.playerAssistKilled = Stats.MAP.getOrDefault(player.getName(), new Data(0, 0, 0, 0)).getAssistKills();
        this.deaths = Stats.MAP.getOrDefault(player.getName(), new Data(0, 0, 0, 0)).getDeaths();
    }

    public static Player to(org.bukkit.entity.Player player) {
        if (player == null) return null;
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

        if (isVIP()) {
            Config.KITS.getSections("kit.vip").forEach(slot -> getInventory().setItem(Integer.parseInt(slot), Config.KITS.getItemStack("kit.vip." + slot)));
        } else {
            Config.KITS.getSections("kit.normal").forEach(slot -> getInventory().setItem(Integer.parseInt(slot), Config.KITS.getItemStack("kit.normal." + slot)));
        }

        updateInventory();

    }

    public void teleportSpawnpoint() {

        this.clearInventoryFully();
        this.giveKit();

        playSound(getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.2F);
        sendTitle("§a§lSTART!", "§7Dai la caccia ai Founder e uccidi tutti!", 10, 60, 10);

    }

    public boolean isVIP() {
        return Objects.requireNonNull(Bukkit.getPlayerExact(getName())).hasPermission("founderhunt.vip");
    }

    public void addPoint() {
        addPoint(100);
    }

    public void addAssistPoint() {
        addPoint(30);
    }

    public void addPoint(int n) {
        int p = Utils.BOOSTER ? (int) (n * (getName().equals(Utils.BOOSTING) ? 2 : 1.5)) : n;

        setPoints(getPoints() + p);
        sendTitle("", "§6Hai ricevuto " + p + " " + (p == 1 ? "punto!" : "punti!"), 5, 30, 5);
        playSound(getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.3F);
    }

    public void remPoint() {
        remPoint(100);
    }

    public void remPoint(int n) {
        if (getPoints() - n < 0)
            setPoints(0);

        setPoints(getPoints() - n);
    }

    public void setPoints(int points) {
        this.points = points;

        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () ->
                Database.update("UPDATE `stats` SET `points`=? WHERE username=?", getPoints(), getName()));
    }

    public void addKill() {
        setPlayerKilled(getPlayerKilled() + 1);

        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () ->
                Database.update("UPDATE `stats` SET `kills`=? WHERE username=?", getPlayerKilled(), getName()));
    }

    public void addDeath() {
        setDeaths(getDeaths() + 1);

        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () ->
                Database.update("UPDATE `stats` SET `deaths`=? WHERE username=?", getDeaths(), getName()));
    }

    public void addAssistKill() {
        setPlayerAssistKilled(getPlayerAssistKilled() + 1);

        Bukkit.getScheduler().runTaskAsynchronously(FounderHunt.inst(), () ->
                Database.update("UPDATE `stats` SET `assists`=? WHERE username=?", getPlayerAssistKilled(), getName()));
    }

    public void setBoosting(boolean value) {
        Database.update("UPDATE stats SET booster=? WHERE username=?;", value, getName());
    }

    public boolean getBoosting() {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM stats WHERE username = ?")) {
            ps.setString(1, getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("booster");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
