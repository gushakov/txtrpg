package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Scene;

import java.time.LocalDateTime;

/**
 * @author gushakov
 */
public class Action {

    private ActionName name;

    private LocalDateTime time;

    private Scene location;

    private Actor initiator;

    public Action(ActionName name) {
        this.name = name;
    }

    public ActionName getName() {
        return name;
    }

    public void setName(ActionName name) {
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
