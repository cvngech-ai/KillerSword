package com.killersword;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class KillerSwordItem {

    public static final int CUSTOM_MODEL_DATA = 1001;

    public static ItemStack create() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "⚔ Killer");

        List<String> lore = Arrays.asList(
            ChatColor.BLUE + "✦ " + ChatColor.GRAY + "Ek baar pakdo, duniya kaanpegi.",
            ChatColor.BLUE + "✦ " + ChatColor.DARK_AQUA + "Strength II" + ChatColor.GRAY + " - Permanent",
            ChatColor.BLUE + "✦ " + ChatColor.GOLD + "Sharpness VII" + ChatColor.GRAY + " - Enchanted",
            ChatColor.BLUE + "✦ " + ChatColor.LIGHT_PURPLE + "Particles" + ChatColor.GRAY + " - Active",
            "",
            ChatColor.DARK_GRAY + "Legendary Weapon"
        );
        meta.setLore(lore);

        meta.addEnchant(Enchantment.DAMAGE_ALL, 7, true);
        meta.setUnbreakable(true);
        meta.setCustomModelData(CUSTOM_MODEL_DATA);

        sword.setItemMeta(meta);
        return sword;
    }

    public static boolean isKillerSword(ItemStack item) {
        if (item == null || item.getType() != Material.DIAMOND_SWORD) return false;
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasCustomModelData() && meta.getCustomModelData() == CUSTOM_MODEL_DATA;
    }
          }
