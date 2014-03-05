package com.github.txtrpg.core;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author gushakov
 */
public class ItemsContainer extends Item implements Container<Item> {

    private int emptyWeight;

    private int capacity;

    private ConcurrentSkipListSet<Item> items;

    public ItemsContainer() {
    }

    public ItemsContainer(String name, String description) {
        super(name, description);
        this.emptyWeight = 0;
        this.capacity = Integer.MAX_VALUE;
//        items = new ConcurrentSkipListSet<>((it1, it2) -> it1.getName().compareTo(it2.getName()));
        items = new ConcurrentSkipListSet<>();
    }

    public ConcurrentSkipListSet<Item> getItems() {
        return items;
    }

    public void setItems(ConcurrentSkipListSet<Item> items) {
        this.items = items;
    }

    public synchronized Optional<Item> take(String name) {
        Optional<Item> item = items.stream().filter(it -> it.getName().equals(name)).findFirst();
        if (item.isPresent()) {
            items.remove(item.get());
        }
        return item;
    }

    public synchronized boolean put(Item item) {
        return canFit(item) && items.add(item);
    }

    public synchronized boolean canFit(Item item) {
        return capacity >= getWeight() + item.getWeight();
    }

    public synchronized boolean isFull() {
        return capacity > getWeight();
    }

    @Override
    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public synchronized int getWeight() {
        return emptyWeight + items.stream().mapToInt(Item::getWeight).sum();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

}
