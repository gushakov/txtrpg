package com.github.txtrpg.json;

import java.util.List;

/**
 * @author gushakov
 */
public class CorpseType extends EntityType {

    private List<ItemType> items;

    public CorpseType() {
    }

    public List<ItemType> getItems() {
        return items;
    }
}
