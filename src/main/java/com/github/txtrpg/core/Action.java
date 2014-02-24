package com.github.txtrpg.core;

import java.time.LocalDateTime;

/**
 * @author gushakov
 */
public class Action {

    private String name;

    private LocalDateTime time;

    private Scene location;

    private Actor initiator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Scene getLocation() {
        return location;
    }

    public void setLocation(Scene location) {
        this.location = location;
    }

    public Actor getInitiator() {
        return initiator;
    }

    public void setInitiator(Actor initiator) {
        this.initiator = initiator;
    }
}
