package com.github.txtrpg.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.*;

/**
 * A location in the world.
 *
 * @author gushakov
 */
public class Scene extends Entity implements Observable {

    @RelatedToVia(type = "EXIT_TO", direction = Direction.OUTGOING, elementClass = Exit.class)
    @Fetch
    private Set<Exit> exits;

    private Container<Item> ground;

    private Room<Actor> room;

    public Scene() {
        this.ground = new Container<>();
        this.room = new Room<>();
    }

    public Scene(String name, String description) {
        super(name, description);
        this.exits = new HashSet<>();
        this.ground = new Container<>();
        this.room = new Room<>();
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void setExits(Set<Exit> exits) {
        this.exits = exits;
    }

    public Container<Item> getGround() {
        return ground;
    }

    public void setGround(Container<Item> ground) {
        this.ground = ground;
    }

    public Room<Actor> getRoom() {
        return room;
    }

    public void setRoom(Room<Actor> room) {
        this.room = room;
    }

    public void addExit(Dir dir, Scene to) {
        exits.add(new Exit(dir, this, to));
    }

    public Optional<Exit> getExit(Dir dir) {
        return exits.stream().filter(e -> e.getDir() == dir).findFirst();
    }

    public Optional<Scene> getExitTo(Dir dir) {
        Optional<Exit> exit = exits.stream().filter(e -> e.getDir() == dir).findFirst();
        return exit.isPresent() ? Optional.of(exit.get().getTo()) : Optional.empty();
    }

    @Override
    public String getDescription() {
        final StringBuilder buffer = new StringBuilder(super.getDescription());
        ground.stream().forEach(it -> {
            buffer.append("\n\r")
                    .append(it.getDescription());
        });
        exits.stream().forEach(e -> {
            buffer.append("\n\r")
                    .append("*")
                    .append(e.getDir().getDirection())
                    .append("*: to ")
                    .append(e.getTo().getName());
        });
        return buffer.toString();
    }

    @Override
    public Collection<Visible> showTo(Actor actor) {
        List<Visible> visibles = new ArrayList<>();
        room.stream().filter(a -> !a.getName().equals(actor.getName())).forEach(visibles::add);
        return visibles;
    }
}
