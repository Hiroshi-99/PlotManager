package org.champa.plotManager.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ItemBuilder;

public class BiomeMenu {
    private final PlotManager plugin;

    public BiomeMenu(PlotManager plugin) {
        this.plugin = plugin;
    }

    public void openBiomeMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, "Biome Selection");

        int slot = 0;
        for (String biome : plugin.getBiomeManager().getAvailableBiomes()) {
            menu.setItem(slot++, new ItemBuilder(XMaterial.GRASS_BLOCK.parseMaterial())
                    .name("&a" + biome)
                    .lore("&7Click to set biome")
                    .build());
        }

        player.openInventory(menu);
    }
}