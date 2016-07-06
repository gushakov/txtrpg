package com.github.txtrpg.json;

/**
 * @author gushakov
 */
public class NpcType extends EntityType {

    private int health;

    private SpawnType spawn;

    private CorpseType corpse;

    public NpcType() {
    }

    public int getHealth() {
        return health;
    }

    public SpawnType getSpawn() {
        return spawn;
    }

    public CorpseType getCorpse() {
        return corpse;
    }
}
