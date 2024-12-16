package org.champa.plotManager.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.data.PlotData;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CacheManager {
    private final PlotManager plugin;
    private final Cache<UUID, PlotData> plotCache;

    public CacheManager(PlotManager plugin) {
        this.plugin = plugin;
        this.plotCache = Caffeine.newBuilder()
                .expireAfterWrite(plugin.getConfigManager().getCacheDuration(), TimeUnit.MINUTES)
                .maximumSize(plugin.getConfigManager().getMaxCacheSize())
                .build();
    }

    public void cachePlotData(UUID playerUUID, PlotData data) {
        plotCache.put(playerUUID, data);
    }

    public PlotData getPlotData(UUID playerUUID) {
        return plotCache.getIfPresent(playerUUID);
    }

    public void invalidateCache(UUID playerUUID) {
        plotCache.invalidate(playerUUID);
    }

    public void cleanup() {
        plotCache.cleanUp();
    }
}