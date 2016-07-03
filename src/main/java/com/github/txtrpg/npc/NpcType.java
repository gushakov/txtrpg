package com.github.txtrpg.npc;

/**
 * @author gushakov
 */
public class NpcType {

    private String name;

    private String description;

    private int health;

    private Spawn spawn;

    public NpcType() {
    }

    public synchronized int getHealth() {
        return health;
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
