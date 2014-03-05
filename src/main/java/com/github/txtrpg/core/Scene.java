package com.github.txtrpg.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A location in the world.
 *
 * @author gushakov
 */
public class Scene extends Entity {

    @RelatedToVia(type = "EXIT_TO", direction = Direction.OUTGOING, elementClass = Exit.class)
    @Fetch
    private Set<Exit> exits;

    private ItemsContainer ground;

    public Scene(){
    }

    public Scene(String name, String description) {
        super(name, description);
        this.exits = new HashSet<>();
        this.ground = new ItemsContainer();
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void setExits(Set<Exit> exits) {
        this.exits = exits;
    }

    public ItemsContainer getGround() {
        return ground;
    }

    public void setGround(ItemsContainer ground) {
        this.ground = ground;
    }

    public void addExit(Dir dir, Scene to) {
        exits.add(new Exit(dir, this, to));
    }

    public Optional<Exit> getExit(Dir dir) {
        return exits.stream().filter(e -> e.getDir() == dir).findFirst();
    }

    public Optional<Scene> getExitTo(Dir dir) {
        Optional<Exit> exit = exits.stream().filter(e -> e.getDir() == dir).findFirst();
        return exit.isPresent() ? Optional.<Scene>of(exit.get().getTo()) : Optional.<Scene>empty();
    }

}
