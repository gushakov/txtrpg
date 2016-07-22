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

    private int capacity;

    public Container() {
        this.items = new CollectionOfEntities<>();
    }

    public Container(String name, String description) {
        super(name, description);
        this.fixed = true;
        this.capacity = Integer.MAX_VALUE;
        this.items = new CollectionOfEntities<>();
    }

    public Container(String name, String description, boolean fixed, int capacity) {
        super(name, description);
        this.fixed = fixed;
        this.capacity = capacity;
        this.items = new CollectionOfEntities<>();
    }

    public Stream<T> stream() {
        synchronized (lock) {
            return items.stream();
        }
    }

    public void setItems(ConcurrentSkipListSet<T> entities) {
        synchronized (lock) {
            items.setEntities(entities);
        }
    }

    public Optional<T> remove(String name) {
        synchronized (lock) {
            return items.remove(name);
        }
    }

    public void remove(T item){
        synchronized (lock){
            items.remove(item);
        }
    }

    public boolean put(T item) {
        synchronized (lock) {
            return canFit(item) && items.add(item);
        }
    }

    public boolean canFit(Item item) {
        synchronized (lock) {
            return capacity >= getWeight() + item.getWeight();
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return items.isEmpty();
        }
    }

    public boolean isFull() {
        synchronized (lock) {
            return capacity > getWeight();
        }
    }

    public List<T> find(String prefix) {
        synchronized (lock) {
            final List<T> list = items.find(prefix);
            return list;
        }
    }

    public int getCapacity() {
        synchronized (lock) {
            return capacity;
        }
    }

    @Override
    public int getWeight() {
        synchronized (lock) {
            return fixed ? Integer.MAX_VALUE : items.stream().mapToInt(Item::getWeight).sum();
        }
    }

    @Override
    public Collection<Visible> showTo(Actor actor) {
        synchronized (lock) {
            return items.stream().map(Visible.class::cast).collect(Collectors.toList());
        }
    }
}
