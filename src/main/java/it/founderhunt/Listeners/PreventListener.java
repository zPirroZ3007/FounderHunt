package it.founderhunt.Listeners;

import it.founderhunt.Objects.Player;
import it.founderhunt.Utils.Perms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PreventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventChat(AsyncPlayerChatEvent event) {
        event.setFormat("§8[Organizzatore] §a" + event.getPlayer().getName() + " §8» §7" + event.getMessage());
        if (event.getMessage().startsWith("/"))
            return;
        if (event.getPlayer().hasPermission(Perms.ADMIN))
            return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void preventItemMove(InventoryClickEvent event) {

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) event.getWhoClicked();
        player.getHandle().updateInventory(player.getHandle().activeContainer);
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

    public static Listener to() {
        return new PreventListener();
    }
}
