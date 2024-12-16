package org.champa.plotManager.manager;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.types.*;
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
            com.plotsquared.core.location.Location plotLocation =
                    com.plotsquared.core.location.Location.at(
                            player.getWorld().getName(),
                            player.getLocation().getBlockX(),
                            player.getLocation().getBlockY(),
                            player.getLocation().getBlockZ()
                    );
            return plotLocation.getPlot();
        });
    }

    public CompletableFuture<Boolean> togglePlotFlag(Plot plot, String flagName, String value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                switch (flagName.toLowerCase()) {
                    case "pvp" -> {
                        BooleanFlag pvpFlag = GlobalFlagContainer.getInstance().getFlag(PvPFlag.class);
                        boolean currentValue = plot.getFlag(pvpFlag);
                        plot.setFlag(pvpFlag, !currentValue);
                        return true;
                    }
                    case "fly" -> {
                        BooleanFlag flyFlag = GlobalFlagContainer.getInstance().getFlag(FlyFlag.class);
                        boolean currentValue = plot.getFlag(flyFlag);
                        plot.setFlag(flyFlag, !currentValue);
                        return true;
                    }
                    case "weather" -> {
                        BooleanFlag weatherFlag = GlobalFlagContainer.getInstance().getFlag(WeatherFlag.class);
                        boolean currentValue = plot.getFlag(weatherFlag);
                        plot.setFlag(weatherFlag, !currentValue);
                        return true;
                    }
                    case "time" -> {
                        if (value != null) {
                            IntegerFlag timeFlag = GlobalFlagContainer.getInstance().getFlag(TimeFlag.class);
                            plot.setFlag(timeFlag, Integer.parseInt(value));
                            return true;
                        }
                        return false;
                    }
                    case "gamemode" -> {
                        if (value != null) {
                            GameModeFlag gameModeFlag = GlobalFlagContainer.getInstance().getFlag(GameModeFlag.class);
                            plot.setFlag(gameModeFlag, value);
                            return true;
                        }
                        return false;
                    }
                    default -> {
                        return false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public void setPlotBiome(Plot plot, String biome) {
        try {
            BiomeFlag biomeFlag = GlobalFlagContainer.getInstance().getFlag(BiomeFlag.class);
            plot.setFlag(biomeFlag, biome.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}