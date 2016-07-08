package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private int health;

    private Scene location;

    public Actor() {
    }

    public Actor(String name, String description, Scene location) {
        super(name, description);
        this.location = location;
    }

    public void setHealth(int health) {
        synchronized (lock) {
            this.health = health;
        }
    }

    public Scene getLocation() {
        synchronized (lock) {
            return location;
        }
    }

    public void setLocation(Scene location) {
        synchronized (lock) {
            this.location = location;
        }
    }

    public int getHealth() {
        synchronized (lock) {
            return health;
        }
    }

    public void decreaseHealth(int amount) {
        synchronized (lock) {
            health -= amount;
        }
    }

    public void increaseHealth(int amount) {
        synchronized (lock) {
            health += amount;
        }
    }

    public synchronized boolean isAlive(){
        synchronized (lock) {
            return health > 0;
        }
    }

}
