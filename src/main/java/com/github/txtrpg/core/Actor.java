package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private Scene location;

    public synchronized Scene getLocation() {
        return location;
    }

    public synchronized void setLocation(Scene location) {
        this.location = location;
    }
}
