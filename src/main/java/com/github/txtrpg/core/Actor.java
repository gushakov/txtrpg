package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private Scene location;

    public Scene getLocation() {
        return location;
    }

    public void setLocation(Scene location) {
        this.location = location;
    }
}
