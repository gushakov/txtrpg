package com.github.txtrpg.core;

import com.fasterxml.uuid.Generators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author gushakov
 */
@NodeEntity
public class Entity implements Visible, Comparable<Entity> {

    @GraphId
    private Long id;

    private String name;

    private String description;

    public Entity() {
        this.name = "e" + Generators.timeBasedGenerator().generate().timestamp();
        this.description = "entity";
    }

    public Entity(String name, String description) {
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
        return name != null && entity != null && entity.getName() != null ? name.compareTo(entity.getName()) : 0;
    }
}
