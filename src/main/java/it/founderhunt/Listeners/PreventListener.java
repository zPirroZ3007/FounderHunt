package it.founderhunt.Listeners;

import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PreventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().hasPermission(Perms.ADMIN))
            return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer().hasPermission(Perms.ADMIN))
            return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventChat(AsyncPlayerChatEvent event) {
        event.setFormat("§8[" + (Utils.isFounder(event.getPlayer().getName()) ? "FounderConnesso" : "Organizzatore") + "] §a" + event.getPlayer().getName() + " §8» §7" + event.getMessage());
        if (event.getMessage().startsWith("/"))
            return;
        if (event.getPlayer().hasPermission(Perms.ADMIN) || Utils.isFounder(event.getPlayer().getName()))
            return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventItemMove(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) event.getWhoClicked();
        if (player.hasPermission(Perms.ADMIN))
            return;

        event.setCancelled(true);
        player.updateInventory();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventRain(WeatherChangeEvent event) {

        if (event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventHungerLose(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        if (event.getEntity() instanceof org.bukkit.entity.Player)
            if (event.getFoodLevel() < 20)
                ((org.bukkit.entity.Player) event.getEntity()).setFoodLevel(20);
    }

    public static Listener to() {
        return new PreventListener();
    }
}
