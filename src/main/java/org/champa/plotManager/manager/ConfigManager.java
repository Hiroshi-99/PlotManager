package org.champa.plotManager.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.champa.plotManager.PlotManager;

import java.util.List;

public class ConfigManager {
    private final PlotManager plugin;
    private FileConfiguration config;

    public ConfigManager(PlotManager plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public String getMenuTitle() {
        return config.getString("settings.menu.main.title", "&8Plot Manager");
    }

    public String getPlotsMenuTitle() {
        return config.getString("settings.menu.plots.title", "&aYour Plots");
    }

    public String getBiomeMenuTitle() {
        return config.getString("settings.menu.biome.title", "&bBiome Selection");
    }

    public String getSettingsMenuTitle() {
        return config.getString("settings.menu.settings.title", "&6Plot Settings");
    }

    public int getMenuSize(String menuType) {
        return config.getInt("settings.menu." + menuType + ".size", 27);
    }

    public List<String> getAllowedBiomes() {
        return config.getStringList("settings.biomes.allowed");
    }

    public List<String> getEnabledFlags() {
        return config.getStringList("settings.flags.enabled");
    }

    public String getMessage(String path) {
        return config.getString("settings.messages." + path, "&cMessage not found: " + path);
    }

    public int getCacheDuration() {
        return config.getInt("settings.cache.duration", 30);
    }

    public int getMaxCacheSize() {
        return config.getInt("settings.cache.max-size", 1000);
    }
    public void reloadConfig() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }
}