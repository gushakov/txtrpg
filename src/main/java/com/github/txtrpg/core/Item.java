package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Item extends Entity {

    private int weight;

    public Item() {
    }

    public Item(String name, String description) {
        super(name, description);
        this.weight = 0;
    }

    public Item(String name, String description, int weight) {
        super(name, description);
        this.weight = weight;
    }

    public int getWeight() {
        synchronized (lock) {
            return weight;
        }
    }

    public void setWeight(int weight) {
        synchronized (lock) {
            this.weight = weight;
        }
    }

}
