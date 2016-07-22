package com.github.txtrpg.core;

import com.fasterxml.uuid.Generators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.concurrent.locks.ReentrantLock;

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

    private boolean locked;

    public Entity() {
        this.locked = false;
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

    public long getUuid() {
        return uuid;
    }

    public void lock(){
        synchronized (lock){
            locked = true;
        }
    }

    public boolean isLocked(){
        synchronized (lock) {
            return locked;
        }
    }

    public void unlock(){
        synchronized (lock) {
            locked = false;
        }
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
