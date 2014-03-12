package com.github.txtrpg.core;

import java.util.stream.Stream;

/**
 * @author gushakov
 */
public class Room<T extends Actor> {

    private CollectionOfEntities<T> actors;

    public Room() {
        this.actors = new CollectionOfEntities<>();
    }

    public Stream<T> stream() {
        return actors.stream();
    }

    public synchronized void enter(T actor) {
        actors.add(actor);
    }

    public synchronized void quit(T actor) {
        actors.remove(actor.getName());
    }
}
