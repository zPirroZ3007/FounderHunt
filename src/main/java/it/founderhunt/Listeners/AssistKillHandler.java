package it.founderhunt.Listeners;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import java.util.Set;

public class AssistKillHandler implements Listener {

    public static Map<String, Set<String>> ASSISTS = Maps.newHashMap();

    @EventHandler(priority = EventPriority.MONITOR)
    public void handleAssist(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        Set<String> damagers = Sets.newHashSet();

        ASSISTS.putIfAbsent(damaged.getName(), damagers);

        damagers = ASSISTS.get(damaged.getName());
        damagers.add(damager.getName());

        ASSISTS.put(damaged.getName(), damagers);

    }

    public static Listener to() {
        return new AssistKillHandler();
    }
}
