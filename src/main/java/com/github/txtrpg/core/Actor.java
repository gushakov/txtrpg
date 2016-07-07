package com.github.txtrpg.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private int health;

    private Scene location;

    private boolean idle;

    public Actor() {
        this.idle = true;
    }

    public Actor(String name, String description, Scene location) {
        super(name, description);
        this.location = location;
        this.idle = true;

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

    public synchronized void setIdle(boolean idle){
        this.idle = idle;
    }

    public synchronized boolean isIdle(){
        return idle;
    }

}
