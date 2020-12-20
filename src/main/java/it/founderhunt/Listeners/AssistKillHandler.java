package it.founderhunt.Listeners;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.founderhunt.Utils.Perms;
import it.founderhunt.Utils.Utils;
import net.tecnocraft.utils.chat.Messenger;
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

        if ((Utils.isScorta(damaged.getName()) || Utils.isFounder(damaged.getName())) && (Utils.isScorta(damager.getName()) || Utils.isFounder(damager.getName()))) {
            event.setCancelled(true);
            return;
        }

        if (KillListener.SPAWNKILL.contains(damaged.getName())) {
            event.setCancelled(true);
            Messenger.sendErrorMessage(damager, String.format("%s è appena spawnato, non puoi colpirlo!", damaged.getName()));
            return;
        } else if (KillListener.SPAWNKILL.contains(damager.getName())) {
            event.setCancelled(true);
            Messenger.sendErrorMessage(damager, "Sei appena spawnato, non puoi colpire nessuno!");
            return;
        }

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
