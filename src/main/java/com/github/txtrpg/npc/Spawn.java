package com.github.txtrpg.npc;

import java.util.List;

/**
 * @author gushakov
 */
public class Spawn {

    private List<String> locations;

    private int number;

    private String chance;

    public Spawn() {
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
