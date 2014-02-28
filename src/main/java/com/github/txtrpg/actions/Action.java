package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Scene;

import java.time.LocalDateTime;

/**
 * @author gushakov
 */
public class Action {

    private ActionName name;

    private Actor initiator;

    private LocalDateTime time;

    private Scene location;

    public Action(ActionName name, Actor initiator) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now();
        this.location = initiator.getLocation();
    }

    public ActionName getName() {
        return name;
    }

    public Actor getInitiator() {
        return initiator;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Scene getLocation() {
        return location;
    }

}
