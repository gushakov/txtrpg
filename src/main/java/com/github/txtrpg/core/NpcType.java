package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class NpcType {

    private String name;

    private String description;

    private Spawn spawn;

    public NpcType() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Spawn getSpawn() {
        return spawn;
    }
}
