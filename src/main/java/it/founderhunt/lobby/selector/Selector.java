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

        contents.set(new SlotPos(1, 2), ClickableItem.of(new ItemStackBuilder(Material.DIAMOND_SWORD, 1).setName("§bPartita #1").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-1"))).build(),
                e -> Utils.sendToServer(player, "game-1")));
        contents.set(new SlotPos(1, 3), ClickableItem.of(new ItemStackBuilder(Material.GOLD_SWORD, 1).setName("§6Partita #2").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-2"))).build(),
                e -> Utils.sendToServer(player, "game-2")));
        contents.set(new SlotPos(1, 4), ClickableItem.of(new ItemStackBuilder(Material.IRON_SWORD, 1).setName("§fPartita #3").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-3"))).build(),
                e -> Utils.sendToServer(player, "game-3")));
        contents.set(new SlotPos(1, 5), ClickableItem.of(new ItemStackBuilder(Material.STONE_SWORD, 1).setName("§7Partita #4").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-4"))).build(),
                e -> Utils.sendToServer(player, "game-4")));
        contents.set(new SlotPos(1, 6), ClickableItem.of(new ItemStackBuilder(Material.WOOD_SWORD, 1).setName("§8Partita #5").addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("§7Clicca per entrare!", String.format("§aOnline: §l%s", Utils.getBungeeOnlineCountIn(player, "game-5"))).build(),
                e -> Utils.sendToServer(player, "game-5")));

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
