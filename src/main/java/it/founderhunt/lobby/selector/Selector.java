package it.founderhunt.lobby.selector;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.SlotPos;
import it.founderhunt.lobby.util.Icons;
import it.founderhunt.lobby.util.ItemStackBuilder;
import it.founderhunt.lobby.util.Utils;
import net.tecnocraft.utils.mains.MainBukkit;
import net.tecnocraft.utils.utils.GUIFramework;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

public class Selector extends GUIFramework implements Listener {
    @Override
    public void execute(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 3), ClickableItem.of(new ItemStackBuilder(Material.DIAMOND_SWORD, 1).setName("§bPartita #1").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-1"))).build(),
                e -> Utils.sendToServer(player, "game-1")));
        contents.set(new SlotPos(1, 5), ClickableItem.of(new ItemStackBuilder(Material.GOLD_SWORD, 1).setName("§6Partita #2").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-2"))).build(),
                e -> Utils.sendToServer(player, "game-2")));

        contents.fillBorders(ClickableItem.empty(Utils.randomGlassColor()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void openSelector(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;
        if (!event.getItem().isSimilar(Icons.SELECTOR))
            return;
        SmartInventory.builder()
                .id("inv")
                .provider(this)
                .manager(MainBukkit.getInventoryManager())
                .size(3, 9)
                .title("Seleziona una partita")
                .build()
                .open(event.getPlayer());
    }

    public static Selector to() {
        return new Selector();
    }
}
