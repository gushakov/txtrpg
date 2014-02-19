package com.github.txtrpg.core;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * @author gushakov
 */
@RelationshipEntity
public class Exit {

    @GraphId
    private Long id;

    private Dir dir;

    @StartNode
    private Scene from;

    @EndNode
    private Scene to;

    public Exit() {
    }

    public Exit(Dir dir, Scene from, Scene to) {
        this.dir = dir;
        this.from = from;
        this.to = to;
    }

    public Long getId() {
        return id;
    }

    public Dir getDir() {
        return dir;
    }

    public Scene getFrom() {
        return from;
    }

    public Scene getTo() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        boolean answer = false;

        if (obj != null && obj.getClass() == this.getClass()){
            if (id == null){
               answer = super.equals(obj);
            }
            else {
               answer = id.equals(((Exit)obj).id);
            }
        }

        return answer;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "Exit: " + id + " from " + from + " via " + dir + " to " + to;
    }
}
