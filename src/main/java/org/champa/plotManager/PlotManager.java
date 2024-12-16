package org.champa.plotManager;

import com.plotsquared.core.PlotAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.champa.plotManager.command.PlotCommand;
import org.champa.plotManager.listener.MenuListener;
import org.champa.plotManager.manager.*;

public class PlotManager extends JavaPlugin {
    private static PlotManager instance;
    private PlotAPI plotAPI;
    private MenuManager menuManager;
    private ConfigManager configManager;
    private PlotHandler plotHandler;
    private BiomeManager biomeManager;
    private CacheManager cacheManager;

    @Override
    public void onEnable() {
        instance = this;
        this.plotAPI = new PlotAPI();

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.cacheManager = new CacheManager(this);
        this.plotHandler = new PlotHandler(this);
        this.biomeManager = new BiomeManager(this);
        this.menuManager = new MenuManager(this);

        // Register command and listener
        getCommand("plot").setExecutor(new PlotCommand(this));
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);

        getLogger().info("PlotManager enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Clean up resources
        if (cacheManager != null) {
            cacheManager.cleanup();
        }
        getLogger().info("PlotManager disabled successfully!");
    }

    // Getter methods
    public static PlotManager getInstance() {
        return instance;
    }

    public PlotAPI getPlotAPI() {
        return plotAPI;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlotHandler getPlotHandler() {
        return plotHandler;
    }

    public BiomeManager getBiomeManager() {
        return biomeManager;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
    public void reloadPluginConfig() {
        configManager.reloadConfig();
    }
}