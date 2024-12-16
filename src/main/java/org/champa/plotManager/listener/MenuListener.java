package org.champa.plotManager.listener;

import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;


public class MenuListener implements Listener {
    private final PlotManager plugin;

    public MenuListener(PlotManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        String title = event.getView().getTitle();

        if (title.equals(ColorUtil.color(plugin.getConfigManager().getMenuTitle()))) {
            event.setCancelled(true);
            handleMainMenu(player, event.getSlot());
        } else if (title.equals(ColorUtil.color("&aYour Plots"))) {
            event.setCancelled(true);
            handlePlotsMenu(player, event.getSlot());
        } else if (title.equals(ColorUtil.color("&bBiome Selection"))) {
            event.setCancelled(true);
            handleBiomeMenu(player, event.getSlot());
        } else if (title.equals(ColorUtil.color("&6Plot Settings"))) {
            event.setCancelled(true);
            handleSettingsMenu(player, event.getSlot());
        }
    }

    private void handleMainMenu(Player player, int slot) {
        switch (slot) {
            case 10 -> plugin.getMenuManager().openPlotsMenu(player);
            case 12 -> plugin.getMenuManager().openSettingsMenu(player);
            case 14 -> plugin.getMenuManager().openBiomeMenu(player);
            case 16 -> plugin.getMenuManager().openStatisticsMenu(player);
        }
    }

    private void handlePlotsMenu(Player player, int slot) {
        plugin.getPlotHandler().getPlayerPlots(player).thenAccept(plots -> {
            if (!plots.isEmpty() && slot < plots.size()) {
                Plot plot = plots.get(slot);
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    plugin.getMenuManager().openPlotActionsMenu(player, plot);
                });
            }
        });
    }

    private void handleBiomeMenu(Player player, int slot) {
        var biomes = plugin.getBiomeManager().getAvailableBiomes();
        if (slot < biomes.size()) {
            String selectedBiome = biomes.get(slot);
            plugin.getPlotHandler().getCurrentPlot(player).thenAccept(plot -> {
                if (plot != null) {
                    plugin.getBiomeManager().changePlotBiome(plot, selectedBiome);
                    player.sendMessage(ColorUtil.color("&aBiome changed successfully!"));
                }
            });
        }
    }

    private void handleSettingsMenu(Player player, int slot) {
        plugin.getPlotHandler().getCurrentPlot(player).thenAccept(plot -> {
            if (plot == null) return;

            switch (slot) {
                case 10 -> togglePvP(plot, player);
                case 12 -> toggleFlight(plot, player);
                case 14 -> toggleWeather(plot, player);
            }
        });
    }

    private void togglePvP(Plot plot, Player player) {
        plugin.getPlotHandler().togglePlotFlag(plot, "pvp", null).thenAccept(success -> {
            if (success) {
                player.sendMessage(ColorUtil.color("&aPvP setting toggled!"));
            }
        });
    }

    private void toggleFlight(Plot plot, Player player) {
        plugin.getPlotHandler().togglePlotFlag(plot, "fly", null).thenAccept(success -> {
            if (success) {
                player.sendMessage(ColorUtil.color("&aFlight setting toggled!"));
            }
        });
    }

    private void toggleWeather(Plot plot, Player player) {
        plugin.getPlotHandler().togglePlotFlag(plot, "weather", null).thenAccept(success -> {
            if (success) {
                player.sendMessage(ColorUtil.color("&aWeather setting toggled!"));
            }
        });
    }
}