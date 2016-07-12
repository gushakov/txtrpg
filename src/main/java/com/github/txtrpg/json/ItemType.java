package com.github.txtrpg.json;

import java.util.List;

/**
 * @author gushakov
 */
public class ItemType extends EntityType {

    private int weight;

    private List<ItemType> items;

    public ItemType() {
    }

    public int getWeight() {
        return weight;
    }

    public List<ItemType> getItems() {
        return items;
    }
}
