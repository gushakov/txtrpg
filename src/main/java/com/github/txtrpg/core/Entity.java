package com.github.txtrpg.core;

import com.fasterxml.uuid.Generators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author gushakov
 */
@NodeEntity
public class Entity implements Visible, Comparable<Entity> {

    protected final Object lock = new Object();

    @GraphId
    private Long id;

    private String name;

    private String description;

    private long uuid;

    public Entity() {
        this.uuid = Generators.timeBasedGenerator().generate().timestamp();
        this.name = "e" + this.uuid;
        this.description = "entity";
    }

    public Entity(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Entity entity) {
        return Long.valueOf(uuid).compareTo(entity.uuid);
    }

    @Override
    public String toString() {
        return name;
    }
}
