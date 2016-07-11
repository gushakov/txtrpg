package com.github.txtrpg.core;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gushakov
 */
public class CollectionOfEntities<T extends Entity> {

    private ConcurrentSkipListSet<T> entities;

    public CollectionOfEntities() {
        entities = new ConcurrentSkipListSet<>();
    }

    public ConcurrentSkipListSet<T> getEntities() {
        return entities;
    }

    public void setEntities(ConcurrentSkipListSet<T> entities) {
        this.entities = entities;
    }

    public Stream<T> stream() {
        return entities.stream();
    }

    public Optional<T> remove(String name) {
        Optional<T> entity = entities.stream().filter(it -> it.getName().equals(name)).findFirst();
        if (entity.isPresent()) {
            entities.remove(entity.get());
        }
        return entity;
    }

    public void remove(T entity){
        entities.remove(entity);
    }

    public synchronized boolean add(T entity) {
        return entities.add(entity);
    }

    public synchronized boolean isEmpty() {
        return entities.isEmpty();
    }

    public synchronized List<T> find(String prefix) {
        Pattern pattern = Pattern.compile("^" + prefix + "|\\s+" + prefix, Pattern.CASE_INSENSITIVE);
        return entities.stream().filter(it -> pattern.matcher(it.getName()).find()).collect(Collectors.toList());
    }
}
