package org.champa.plotManager.manager;

import com.plotsquared.core.plot.Plot;
import org.bukkit.block.Biome;
import org.champa.plotManager.PlotManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BiomeManager {
    private final PlotManager plugin;
    private final List<String> availableBiomes;

    public BiomeManager(PlotManager plugin) {
        this.plugin = plugin;
        this.availableBiomes = Arrays.stream(Biome.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<String> getAvailableBiomes() {
        return availableBiomes;
    }

    public void changePlotBiome(Plot plot, String biome) {
        try {
            // Set the biome using the plot's area
            plot.getArea().setComponent("biome", biome.toLowerCase(), null, null);
        } catch (Exception e) {
            plugin.getLogger().warning("Error setting biome: " + e.getMessage());
            e.printStackTrace();
        }
    }
}