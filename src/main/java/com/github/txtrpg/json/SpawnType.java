package com.github.txtrpg.json;

import java.util.List;

/**
 * @author gushakov
 */
public class SpawnType {

    private List<String> locations;

    private int number;

    private String chance;

    public SpawnType() {
    }

    public List<String> getLocations() {
        return locations;
    }

    public int getNumber() {
        return number;
    }

    public String getChance() {
        return chance;
    }
}
