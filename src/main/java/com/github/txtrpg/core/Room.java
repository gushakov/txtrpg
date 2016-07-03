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

    public synchronized void add(T actor) {
        actors.add(actor);
    }

    public synchronized void remove(T actor) {
        actors.remove(actor.getName());
    }

    public synchronized Stream<Player> getOtherPlayers(Player player) {
        return actors.stream().filter(a -> a instanceof Player && !a.equals(player))
                .map(Player.class::cast);
    }

    public synchronized Stream<Player> getOtherMatchingPlayers(Player player, String prefix) {
        return getOtherPlayers(player).filter(p -> p.getName().toLowerCase().startsWith(prefix.toLowerCase()));
    }

    public synchronized Stream<Npc> getNpcs(){
        return actors.stream().filter(Npc.class::isInstance).map(Npc.class::cast);
    }

    public synchronized Stream<T> getOtherActors(Actor actor) {
        return actors.stream().filter(a -> !a.equals(actor));
    }

    public synchronized Stream<T> getOtherMatchingActors(Actor actor, String prefix){
        return getOtherActors(actor).filter(a -> a.getName().toLowerCase().startsWith(prefix.toLowerCase()));
    }
}
