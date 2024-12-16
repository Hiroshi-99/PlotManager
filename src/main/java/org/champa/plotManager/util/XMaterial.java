package org.champa.plotManager.util;

import org.bukkit.Material;

public enum XMaterial {
    MAP(Material.MAP),
    COMPARATOR(Material.COMPARATOR),
    GRASS_BLOCK(Material.GRASS_BLOCK),
    BOOK(Material.BOOK),
    REDSTONE_TORCH(Material.REDSTONE_TORCH),
    FEATHER(Material.FEATHER),
    CLOCK(Material.CLOCK),
    WATER_BUCKET(Material.WATER_BUCKET);

    private final Material material;

    XMaterial(Material material) {
        this.material = material;
    }

    public Material parseMaterial() {
        return material;
    }
}