package com.github.txtrpg.core;

import java.util.List;
import java.util.stream.Collectors;
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

    public synchronized void leave(T actor) {
        actors.remove(actor.getName());
    }

    public synchronized Stream<Player> getOtherPlayers(Player player) {
        return actors.stream().filter(a -> a instanceof Player && !a.equals(player))
                .map(Player.class::cast);
    }

    public synchronized List<Player> getMatchingPlayers(String prefix) {
        return actors.stream()
                .filter(a -> a instanceof Player && a.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .map(Player.class::cast).collect(Collectors.toList());
    }

    public synchronized Stream<T> getOtherActors(Actor actor) {
        return actors.stream().filter(a -> !a.equals(actor));
    }
}
