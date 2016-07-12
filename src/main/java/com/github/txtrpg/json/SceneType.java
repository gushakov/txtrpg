package com.github.txtrpg.json;

import java.util.Set;

/**
 * @author gushakov
 */
public class SceneType extends EntityType {

    private Set<ExitType> exits;

    private ItemType ground;

    public SceneType() {
    }

    public Set<ExitType> getExits() {
        return exits;
    }

    public ItemType getGround() {
        return ground;
    }
}
