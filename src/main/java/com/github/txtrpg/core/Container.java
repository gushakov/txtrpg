package com.github.txtrpg.core;

import java.util.List;
import java.util.Optional;

/**
 * @author gushakov
 */
public interface Container<T extends Movable> {

    public int getCapacity();

    public Optional<T> take(String name);

    public boolean put(T item);

    public boolean canFit(T item);

    public boolean isFull();

    public boolean isEmpty();

    public List<T> suggest(String prefix);
}
