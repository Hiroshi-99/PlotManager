package org.champa.plotManager.manager;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;
import org.champa.plotManager.PlotManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlotHandler {
    private final PlotManager plugin;
    private final PlotAPI plotAPI;

    public PlotHandler(PlotManager plugin) {
        this.plugin = plugin;
        this.plotAPI = new PlotAPI();
    }

    public CompletableFuture<List<Plot>> getPlayerPlots(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            PlotPlayer<?> plotPlayer = PlotPlayer.from(player);
            Set<Plot> plots = plotPlayer.getPlots();
            return new ArrayList<>(plots);
        });
    }

    public CompletableFuture<Plot> getCurrentPlot(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            Location location = player.getLocation();
            return plotAPI.getPlot(location);
        });
    }

    public CompletableFuture<Boolean> togglePlotFlag(Plot plot, String flag, String value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (value == null) {
                    // Toggle boolean flag
                    boolean currentValue = Boolean.parseBoolean(plot.getFlag(flag));
                    plot.setFlag(flag, String.valueOf(!currentValue));
                } else {
                    // Set specific value
                    plot.setFlag(flag, value);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}