package org.champa.plotManager.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.menu.BiomeMenu;
import org.champa.plotManager.menu.PlotMenu;
import org.champa.plotManager.menu.SettingsMenu;
import org.champa.plotManager.util.ColorUtil;
import org.champa.plotManager.util.ItemBuilder;
import org.bukkit.Material;
import com.plotsquared.core.plot.Plot;
import java.util.List;
import org.champa.plotManager.menu.StatisticsMenu;

public class MenuManager {
    private final PlotManager plugin;
    private final PlotMenu plotMenu;
    private final BiomeMenu biomeMenu;
    private final SettingsMenu settingsMenu;
    private final StatisticsMenu statisticsMenu;

    public MenuManager(PlotManager plugin) {
        this.plugin = plugin;
        this.plotMenu = new PlotMenu(plugin);
        this.biomeMenu = new BiomeMenu(plugin);
        this.settingsMenu = new SettingsMenu(plugin);
        this.statisticsMenu = new StatisticsMenu(plugin);
    }

    public void openMainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27,
                ColorUtil.color(plugin.getConfigManager().getMenuTitle()));

        // Your Plots Button
        menu.setItem(10, new ItemBuilder(Material.MAP)
                .name("&aYour Plots")
                .lore("&7Click to view your plots")
                .build());

        // Settings Button
        menu.setItem(12, new ItemBuilder(Material.COMPARATOR)
                .name("&6Settings")
                .lore("&7Manage plot settings")
                .build());

        // Biome Button
        menu.setItem(14, new ItemBuilder(Material.GRASS_BLOCK)
                .name("&bBiome")
                .lore("&7Change plot biome")
                .build());

        // Statistics Button
        menu.setItem(16, new ItemBuilder(Material.BOOK)
                .name("&eStatistics")
                .lore("&7View plot stats")
                .build());

        player.openInventory(menu);
    }


    public void openPlotsMenu(Player player) {
        plotMenu.openPlotsMenu(player);
    }

    public void openBiomeMenu(Player player) {
        biomeMenu.openBiomeMenu(player);
    }

    public void openSettingsMenu(Player player) {
        // Get the player's current plot
        plugin.getPlotHandler().getCurrentPlot(player).thenAccept(plot -> {
            if (plot != null) {
                Bukkit.getScheduler().runTask(plugin, () ->
                        settingsMenu.openSettingsMenu(player, plot));
            } else {
                player.sendMessage(ColorUtil.color("&cYou must be standing in a plot!"));
            }
        });
    }
    public void openStatisticsMenu(Player player) {
        statisticsMenu.openStatisticsMenu(player);
    }
    public void openPlotActionsMenu(Player player, Plot plot) {
        Inventory menu = Bukkit.createInventory(null, 27, ColorUtil.color("&aPlot Actions"));

        // Plot Info
        menu.setItem(4, new ItemBuilder(Material.MAP)
                .name("&ePlot Information")
                .lore(
                        "&7ID: &f" + plot.getId(),
                        "&7World: &f" + plot.getWorldName(),
                        "&7Owner: &f" + plot.getOwnerAbs(),
                        "&7Members: &f" + plot.getMembers().size(),
                        "&7Trusted: &f" + plot.getTrusted().size()
                )
                .build());

        // Plot Actions
        menu.setItem(11, new ItemBuilder(Material.GRASS_BLOCK)
                .name("&aBiome")
                .lore("&7Change plot biome")
                .build());

        menu.setItem(13, new ItemBuilder(Material.REDSTONE)
                .name("&cSettings")
                .lore("&7Manage plot settings")
                .build());

        menu.setItem(15, new ItemBuilder(Material.PLAYER_HEAD)
                .name("&bMembers")
                .lore("&7Manage plot members")
                .build());

        player.openInventory(menu);
    }
}