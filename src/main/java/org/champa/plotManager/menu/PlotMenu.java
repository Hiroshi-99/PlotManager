package org.champa.plotManager.menu;

import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ItemBuilder;

import java.util.List;

public class PlotMenu {
    private final PlotManager plugin;

    public PlotMenu(PlotManager plugin) {
        this.plugin = plugin;
    }

    public void openPlotsMenu(Player player) {
        plugin.getPlotHandler().getPlayerPlots(player).thenAccept(plots -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Inventory menu = Bukkit.createInventory(null, 54, "Your Plots");
                displayPlots(menu, plots);
                player.openInventory(menu);
            });
        });
    }

    private void displayPlots(Inventory menu, List<Plot> plots) {
        int slot = 0;
        for (Plot plot : plots) {
            menu.setItem(slot++, new ItemBuilder(XMaterial.MAP.parseMaterial())
                    .name("&aPlot: " + plot.getId())
                    .lore(
                            "&7Location: " + plot.getWorldName(),
                            "&7Members: " + plot.getMembers().size(),
                            "&7Trusted: " + plot.getTrusted().size(),
                            "&7Denied: " + plot.getDenied().size(),
                            "",
                            "&eClick to manage this plot"
                    )
                    .build());
        }
    }
}