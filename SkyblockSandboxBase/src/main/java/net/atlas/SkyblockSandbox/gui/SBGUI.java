package net.atlas.SkyblockSandbox.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import net.atlas.SkyblockSandbox.item.SBItemStack;
import net.atlas.SkyblockSandbox.player.SBPlayer;
import net.atlas.SkyblockSandbox.util.Logger;
import net.atlas.SkyblockSandbox.util.SUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class SBGUI {

    private final SBPlayer owner;

    protected ItemStack FILLER_GLASS = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)15)).name(Component.text(SUtil.colorize("&7"))).build();

    public SBGUI(SBPlayer owner) {
        this.owner = owner;
    }

    public abstract String getTitle();

    public abstract int getRows();

    public abstract void setItems();

    public ItemStack makeColorfulItem(Material material, String displayName, int amount, int durability, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SUtil.colorize(displayName));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        meta.setLore(SUtil.colorize(Arrays.asList(lore)));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeColorfulItem(Material material, String displayName, int amount, int durability, String lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SUtil.colorize(displayName));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore1 = lore.split("\n");
        meta.setLore(SUtil.colorize(Arrays.asList(lore1)));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeColorfulItem(Material material, String displayName, int amount, int durability, String lore, boolean glowing) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SUtil.colorize(displayName));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if(glowing) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        String[] lore1 = lore.split("\n");
        meta.setLore(SUtil.colorize(Arrays.asList(lore1)));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeColorfulItem(Material material, String displayName, int amount, int durability, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(SUtil.colorize(displayName));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore1 = lore.toArray(new String[0]);
        meta.setLore(SUtil.colorize(Arrays.asList(lore1)));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack makeColorfulSkullItem(String displayname, String owner, int amount, String lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (Bukkit.getPlayer(owner) != null && Bukkit.getPlayer(owner).isValid()) {
            meta.setOwner(owner);
        } else {
            SBItemStack i = new SBItemStack(item);
            List<Component> lore2 = new ArrayList<>();
            String[] loreArr = lore.split("\n");
            for(String s:loreArr) {
                lore2.add(Component.text(SUtil.colorize(s)));
            }

            owner = new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", owner).getBytes()));
            item = ItemBuilder.skull(item).texture(owner).name(Component.text(SUtil.colorize(displayname))).amount(amount).lore(lore2).build();
            return item;
        }
        String[] lore1 = lore.split("\n");
        meta.setLore(SUtil.colorize(Arrays.asList(lore1)));
        meta.setDisplayName(SUtil.colorize(displayname));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack makeColorfulSkullItem(String displayname, String owner, int amount, List<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (Bukkit.getPlayer(owner) != null && Bukkit.getPlayer(owner).isValid()) {
            meta.setOwner(owner);
        } else {
            SBItemStack i = new SBItemStack(item);
            List<Component> lore2 = new ArrayList<>();
            for(String s:lore) {
                lore2.add(Component.text(SUtil.colorize(s)));
            }

            owner = new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", owner).getBytes()));
            item = ItemBuilder.skull(item).texture(owner).name(Component.text(SUtil.colorize(displayname))).amount(amount).lore(lore2).build();
            return item;
        }
        meta.setLore(SUtil.colorize(lore));
        meta.setDisplayName(SUtil.colorize(displayname));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack makeColorfulSkullItem(String displayname, String owner, int amount, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (Bukkit.getPlayer(owner) != null && Bukkit.getPlayer(owner).isValid()) {
            meta.setOwner(owner);
        } else {
            SBItemStack i = new SBItemStack(item);
            List<Component> lore2 = new ArrayList<>();
            for(String s:lore) {
                lore2.add(Component.text(SUtil.colorize(s)));
            }

            owner = new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", owner).getBytes()));
            item = ItemBuilder.skull(item).texture(owner).name(Component.text(SUtil.colorize(displayname))).amount(amount).lore(lore2).build();
            return item;
        }
        meta.setLore(SUtil.colorize(lore));
        meta.setDisplayName(SUtil.colorize(displayname));
        item.setItemMeta(meta);

        return item;
    }
}
