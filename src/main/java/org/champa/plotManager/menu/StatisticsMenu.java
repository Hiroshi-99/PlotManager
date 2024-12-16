package org.champa.plotManager.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;
import org.champa.plotManager.util.ItemBuilder;

public class StatisticsMenu {
    private final PlotManager plugin;

    public StatisticsMenu(PlotManager plugin) {
        this.plugin = plugin;
    }

    public void openStatisticsMenu(Player player) {
        plugin.getPlotHandler().getPlayerPlots(player).thenAccept(plots -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Inventory menu = Bukkit.createInventory(null, 27, ColorUtil.color("&ePlot Statistics"));

                // Total Plots
                menu.setItem(10, new ItemBuilder(Material.MAP)
                        .name("&aTotal Plots")
                        .lore("&7You have &f" + plots.size() + " &7plots")
                        .build());

                // Calculate other statistics
                int trustedPlayers = plots.stream()
                        .mapToInt(plot -> plot.getTrusted().size())
                        .sum();

                int members = plots.stream()
                        .mapToInt(plot -> plot.getMembers().size())
                        .sum();

                // Trusted Players
                menu.setItem(12, new ItemBuilder(Material.PLAYER_HEAD)
                        .name("&bTrusted Players")
                        .lore("&7Total: &f" + trustedPlayers)
                        .build());

                // Members
                menu.setItem(14, new ItemBuilder(Material.DIAMOND)
                        .name("&eMembers")
                        .lore("&7Total: &f" + members)
                        .build());

                // Plot Rating (if supported by your PlotSquared version)
                menu.setItem(16, new ItemBuilder(Material.GOLD_INGOT)
                        .name("&6Plot Ratings")
                        .lore("&7Average Rating: &f" + calculateAverageRating(plots))
                        .build());

                player.openInventory(menu);
            });
        });
    }

    private double calculateAverageRating(List<Plot> plots) {
        return plots.stream()
                .mapToDouble(plot -> plot.getAverageRating())
                .average()
                .orElse(0.0);
    }
}