package com.github.txtrpg.core;

import java.util.concurrent.atomic.AtomicInteger;

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

    public synchronized void setHealth(int health){
      this.health = health;
    }

    public synchronized Scene getLocation() {
        return location;
    }

    public synchronized void setLocation(Scene location) {
        this.location = location;
    }

    public synchronized int getHealth(){
        return health;
    }

    public synchronized void decreaseHealth(int amount){
        health -= amount;
    }

    public synchronized void increaseHealth(int amount){
        health += amount;
    }

    public synchronized boolean isAlive(){
        return health > 0;
    }

}
