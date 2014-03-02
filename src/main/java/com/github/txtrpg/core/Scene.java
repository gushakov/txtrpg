package com.github.txtrpg.core;

import com.fasterxml.uuid.Generators;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
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

    public Scene() {
        super();
        this.exits = new HashSet<>();
    }

    public Scene(String name) {
        super(name);
        this.exits = new HashSet<>();
    }

    public Scene(String name, String description){
        super(name, description);
        this.exits = new HashSet<>();
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void setExits(Set<Exit> exits) {
        this.exits = exits;
    }

    public void addExit(Dir dir, Scene to) {
        exits.add(new Exit(dir, this, to));
    }

    public Exit getExit(Dir dir){
       return exits.stream().filter(e -> e.getDir() == dir).findFirst().get();
    }

}
