package it.founderhunt.lobby.objects;

import it.founderhunt.lobby.packets.ActionBar;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class LobbyPlayer extends CraftPlayer {

    public LobbyPlayer(Player player) {
        super((CraftServer) player.getServer(), ((CraftPlayer) player).getHandle());
    }

    public static LobbyPlayer to(Player entity) {
        if (entity == null) return null;
        return new LobbyPlayer(entity);
    }

    public void addPotionEffects(PotionEffect... effects) {
        for (PotionEffect effect : effects)
            addPotionEffect(effect);
    }

    public void sendActionBarMessage(String message) {
        ActionBar actionBar = new ActionBar(message);
        actionBar.sendToPlayer(this);
    }
}
