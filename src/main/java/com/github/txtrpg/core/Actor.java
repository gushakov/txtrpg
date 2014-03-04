package com.github.txtrpg.core;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.MoveAction;

import java.util.Optional;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private Scene location;

    public synchronized Scene getLocation() {
        return location;
    }

    public synchronized void setLocation(Scene location) {
        this.location = location;
    }

    public synchronized boolean doMove(MoveAction action, ActionProcessor actionProcessor){
        boolean success = false;
        Optional<Scene> to = location.getExitTo(action.getDir());
        if (to.isPresent()){
            location = to.get();
            success = true;
        }
        return  success;
    }

}
