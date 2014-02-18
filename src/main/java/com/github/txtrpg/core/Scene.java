package com.github.txtrpg.core;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A location in the world.
 *
 * @author gushakov
 */
@NodeEntity
public class Scene {

    @GraphId
    private Long id;

    private String name;

    @RelatedToVia
    private @Fetch Set<Exit> exits;

    public Scene() {
    }

    public Scene(String name) {
        this.name = name;
        this.exits = new HashSet<Exit>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void addExit(Exit exit) {
        exits.add(exit);
    }

    @Override
    public boolean equals(Object obj) {
        boolean answer = false;

        if (obj != null && obj.getClass() == this.getClass()) {
            if (id == null) {
                answer = super.equals(obj);
            } else {
                answer = id.equals(((Scene) obj).id);
            }
        }

        return answer;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
