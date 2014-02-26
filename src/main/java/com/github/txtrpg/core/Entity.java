package com.github.txtrpg.core;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author gushakov
 */
@NodeEntity
public class Entity implements Visible {

    @GraphId
    private Long id;

    private String name;

    private String description;

    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
