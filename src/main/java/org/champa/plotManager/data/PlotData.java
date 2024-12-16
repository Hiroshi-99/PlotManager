package org.champa.plotManager.data;

import java.util.UUID;

public class PlotData {
    private final UUID playerUUID;
    private int plotCount;
    private long lastUpdate;

    public PlotData(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.plotCount = 0;
        this.lastUpdate = System.currentTimeMillis();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getPlotCount() {
        return plotCount;
    }

    public void setPlotCount(int plotCount) {
        this.plotCount = plotCount;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void updateTimestamp() {
        this.lastUpdate = System.currentTimeMillis();
    }
}