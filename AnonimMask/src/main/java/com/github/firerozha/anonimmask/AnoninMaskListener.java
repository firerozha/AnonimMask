package com.github.firerozha.anonimmask;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.github.firerozha.anonimmask.AnonimMask.getChat;


public class AnoninMaskListener implements Listener {

    private final AnonimMask plugin;

    public AnoninMaskListener(AnonimMask plugin) {
        this.plugin = plugin;
    }

    HideManager manager = HideManager.inst;

    public static ItemMeta metaConfigItem;


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();
            ItemStack item = p.getInventory().getItemInMainHand();
            String displayName = plugin.getConfig().getString("item.displayName");
            Material itemMaterial = Material.valueOf(plugin.getConfig().getString("item.itemMaterial"));
            List<String> lore = plugin.getConfig().getStringList("item.lore");

            ItemStack configItem = new ItemStack(itemMaterial);
            metaConfigItem = configItem.getItemMeta();
            metaConfigItem.setDisplayName(displayName);
            metaConfigItem.setLore(lore);
            configItem.setItemMeta(metaConfigItem);
            if(item.getItemMeta().equals(metaConfigItem) && item.getType().equals(itemMaterial)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("anonymousMessage")));
                p.getInventory().setHelmet(configItem);
                p.getInventory().removeItem(item);
                p.setPlayerListName("Anonymous");
                p.setDisplayName(ChatColor.translateAlternateColorCodes('&', getChat().getPlayerPrefix(p) + "Anon"));
                p.chat("/skin " + plugin.getConfig().getString("skinName"));
                manager.hidePlayer(p);


            }
        } catch(NullPointerException npe) {
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getHolder().equals(p) && e.getSlot() == 39 && e.getCurrentItem().getItemMeta().equals(metaConfigItem)) {
            p.chat("/skin reset");
            p.setDisplayName(ChatColor.translateAlternateColorCodes('&', getChat().getPlayerPrefix(p) + p.getName()));
            p.setPlayerListName(p.getName());
            manager.unHidePlayer(p);
        }
    }


    }
