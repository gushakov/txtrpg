package com.github.txtrpg.core;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gushakov
 */
public class Container<T extends Item> extends Item implements Observable {

    private CollectionOfEntities<T> items;

    private boolean fixed;

    private int emptyWeight;

    private int capacity;

    public Container() {
        this.items = new CollectionOfEntities<>();
    }

    public Container(String name, String description) {
        super(name, description);
        this.fixed = true;
        this.emptyWeight = Integer.MAX_VALUE;
        this.capacity = Integer.MAX_VALUE;
        this.items = new CollectionOfEntities<>();
    }

    public Container(String name, String description, int emptyWeight) {
        super(name, description, emptyWeight);
        this.fixed = false;
        this.emptyWeight = emptyWeight;
        this.capacity = Integer.MAX_VALUE;
        this.items = new CollectionOfEntities<>();
    }

    public Stream<T> stream() {
        return items.stream();
    }

    public void setItems(ConcurrentSkipListSet<T> entities) {
        items.setEntities(entities);
    }

    public synchronized Optional<T> take(String name) {
        return items.remove(name);
    }

    public synchronized boolean put(T item) {
        return canFit(item) && items.add(item);
    }

    public synchronized boolean canFit(Item item) {
        return capacity >= getWeight() + item.getWeight();
    }

    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }

    public synchronized boolean isFull() {
        return capacity > getWeight();
    }

    public synchronized List<T> find(String prefix) {
        return items.find(prefix);
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public synchronized int getWeight() {
        return fixed ? emptyWeight : emptyWeight + items.stream().mapToInt(Item::getWeight).sum();
    }

    @Override
    public Collection<Visible> showTo(Actor actor) {
        return items.stream().map(Visible.class::cast).collect(Collectors.toList());
    }
}
