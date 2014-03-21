package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private Scene location;

    public Actor() {
    }

    public Actor(String name, String description, Scene location) {
        super(name, description);
        this.location = location;
    }

    public synchronized Scene getLocation() {
        return location;
    }

    public synchronized void setLocation(Scene location) {
        this.location = location;
    }


}
