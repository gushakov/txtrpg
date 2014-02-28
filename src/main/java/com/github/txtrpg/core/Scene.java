package com.github.txtrpg.core;

import com.fasterxml.uuid.Generators;
import org.neo4j.graphdb.Direction;
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
    private Set<Exit> exits;

    public Scene() {
        setId(Generators.timeBasedGenerator().generate().timestamp());
    }

    public Scene(String name) {
        super(name);
        this.exits = new HashSet<>();
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void addExit(Dir dir, Scene to) {
        exits.add(new Exit(dir, this, to));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Scene && ((Scene) obj).getId().equals(getId());
    }
}
