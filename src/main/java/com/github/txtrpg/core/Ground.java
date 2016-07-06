package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Ground extends Container<Item> {

    @Override
    public synchronized boolean canFit(Item item) {
        return true;
    }
}
