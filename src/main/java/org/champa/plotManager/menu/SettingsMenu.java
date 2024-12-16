package org.champa.plotManager.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;
import org.champa.plotManager.util.ItemBuilder;
import com.plotsquared.core.plot.Plot;

public class SettingsMenu {
    private final PlotManager plugin;

    public SettingsMenu(PlotManager plugin) {
        this.plugin = plugin;
    }

    public void openSettingsMenu(Player player, Plot plot) {
        Inventory menu = Bukkit.createInventory(null, 27,
                ColorUtil.color(plugin.getConfigManager().getSettingsMenuTitle()));

        // PvP Toggle
        menu.setItem(10, new ItemBuilder(Material.IRON_SWORD)
                .name("&cToggle PvP")
                .lore("&7Click to toggle PvP in your plot")
                .build());

        // Flight Toggle
        menu.setItem(12, new ItemBuilder(Material.FEATHER)
                .name("&bToggle Flight")
                .lore("&7Click to toggle flight in your plot")
                .build());

        // Weather Toggle
        menu.setItem(14, new ItemBuilder(Material.WATER_BUCKET)
                .name("&9Toggle Weather")
                .lore("&7Click to toggle weather in your plot")
                .build());

        player.openInventory(menu);
    }
}