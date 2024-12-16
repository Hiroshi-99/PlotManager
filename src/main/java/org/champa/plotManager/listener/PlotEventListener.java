package org.champa.plotManager.listener;

import com.plotsquared.core.events.*;
import com.plotsquared.core.events.plot.*;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;

public class PlotEventListener implements Listener {
    private final PlotManager plugin;

    public PlotEventListener(PlotManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlotEnter(PlayerEnterPlotEvent event) {
        Player player = event.getPlayer().getPlayer();
        Plot plot = event.getPlot();

        player.sendMessage(ColorUtil.color("&aEntering plot " + plot.getId()));
    }

    @EventHandler
    public void onPlotLeave(PlayerLeavePlotEvent event) {
        Player player = event.getPlayer().getPlayer();
        Plot plot = event.getPlot();

        player.sendMessage(ColorUtil.color("&cLeaving plot " + plot.getId()));
    }

    @EventHandler
    public void onPlotClaim(PlotClaimEvent event) {
        Plot plot = event.getPlot();
        Player player = event.getPlayer().getPlayer();

        // Set default flags for new plots
        plugin.getPlotHandler().togglePlotFlag(plot, "pvp", "false");
        plugin.getPlotHandler().togglePlotFlag(plot, "fly", "true");

        player.sendMessage(ColorUtil.color("&aClaimed plot " + plot.getId()));
    }

    @EventHandler
    public void onPlotDelete(PlotDeleteEvent event) {
        Plot plot = event.getPlot();
        plugin.getCacheManager().invalidateCache(plot.getOwnerAbs());
    }

    @EventHandler
    public void onFlagChange(PlotFlagAddEvent event) {
        Plot plot = event.getPlot();
        String flag = event.getFlag().getName();
        String value = event.getValue().toString();

        plugin.getLogger().info("Flag " + flag + " changed to " + value + " for plot " + plot.getId());
    }
}