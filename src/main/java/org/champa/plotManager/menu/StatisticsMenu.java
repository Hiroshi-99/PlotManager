package org.champa.plotManager.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;
import org.champa.plotManager.util.ItemBuilder;
import com.plotsquared.core.plot.Plot;
import java.util.List;

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

                // Plot Activity Score instead of Rating
                menu.setItem(16, new ItemBuilder(Material.GOLD_INGOT)
                        .name("&6Plot Activity")
                        .lore(
                                "&7Activity Score: &f" + calculateActivityScore(plots),
                                "&7Based on members and trusted players"
                        )
                        .build());

                player.openInventory(menu);
            });
        });
    }

    private double calculateActivityScore(List<Plot> plots) {
        if (plots.isEmpty()) {
            return 0.0;
        }

        // Calculate an activity score based on members and trusted players
        double totalScore = plots.stream()
                .mapToDouble(plot ->
                        (plot.getMembers().size() * 2.0) + // Members count double
                                (plot.getTrusted().size() * 1.0)   // Trusted players count single
                )
                .sum();

        return Math.round((totalScore / plots.size()) * 10.0) / 10.0; // Round to 1 decimal place
    }
}