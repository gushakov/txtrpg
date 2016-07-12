package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Bag extends Container<Item> {

    public Bag(String name, String description, int capacity) {
        super(name, description, false, capacity);
    }
}
