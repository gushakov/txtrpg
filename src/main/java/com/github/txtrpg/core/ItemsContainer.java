package com.github.txtrpg.core;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public synchronized List<Item> suggest(String prefix) {
        Pattern pattern = Pattern.compile("^" + prefix + "|\\s+" + prefix, Pattern.CASE_INSENSITIVE);
        return items.stream().filter(it -> pattern.matcher(it.getDescription()).find()).collect(Collectors.toList());
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
