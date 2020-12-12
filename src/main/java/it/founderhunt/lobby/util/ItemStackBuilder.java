package it.founderhunt.lobby.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ItemStackBuilder {
    private Material material;
    private final List<String> lore = new ArrayList();
    private List<ItemFlag> flagsL = new ArrayList();
    private String name;
    private int quantity;
    private String skullName;
    private ItemStack item;

    public ItemStackBuilder() {
    }

    public ItemStackBuilder(Material material, int quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public ItemStackBuilder setItem(Material material, int quantity) {
        this.material = material;
        this.quantity = quantity;
        return this;
    }

    public ItemStackBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemStackBuilder setLore(String... strings) {
        this.lore.addAll(Arrays.asList(strings));
        return this;
    }

    public ItemStackBuilder setLore(List<String> strings) {
        this.lore.addAll(strings);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder skullName(String name) {
        this.skullName = name;
        return this;
    }

    public ItemStackBuilder setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        this.flagsL.addAll(Arrays.asList(flags));
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = this.item == null ? new ItemStack(this.material, this.quantity) : this.item;
        ItemMeta defaultMeta = itemStack.getItemMeta();
        ItemStack finalItem;
        Iterator var5;
        ItemFlag flag;

        finalItem = itemStack.clone();
        ItemMeta itemMeta = finalItem.getItemMeta();

        assert itemMeta != null;

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
        itemMeta.setLore(this.lore);
        var5 = this.flagsL.iterator();

        while (var5.hasNext()) {
            flag = (ItemFlag) var5.next();
            itemMeta.addItemFlags(new ItemFlag[]{flag});
        }

        finalItem.setItemMeta(itemMeta);

        ItemStack var11;
        try {
            var11 = finalItem;
        } finally {
            itemStack.setItemMeta(defaultMeta);
        }

        return var11;
    }
}
