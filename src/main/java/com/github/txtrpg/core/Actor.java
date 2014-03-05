package com.github.txtrpg.core;

import com.github.txtrpg.actions.ActionProcessor;

import java.util.Optional;

/**
 * @author gushakov
 */
public class Actor extends Entity {

    private Scene location;

    private ActionProcessor actionProcessor;

    public Actor(Scene location, ActionProcessor actionProcessor) {
        this.location = location;
        this.actionProcessor = actionProcessor;
    }

    public Actor(String name, Scene location, ActionProcessor actionProcessor) {
        super(name);
        this.location = location;
        this.actionProcessor = actionProcessor;
    }

    public Actor(String name, String description, Scene location, ActionProcessor actionProcessor) {
        super(name, description);
        this.location = location;
        this.actionProcessor = actionProcessor;
    }

    public synchronized Scene getLocation() {
        return location;
    }

    public synchronized void setLocation(Scene location) {
        this.location = location;
    }

    public ActionProcessor getActionProcessor() {
        return actionProcessor;
    }

    public synchronized boolean doWelcome(){
        return true;
    }

    public synchronized boolean doMove(Dir dir){
        boolean success = false;
        Optional<Scene> to = location.getExitTo(dir);
        if (to.isPresent()){
            location = to.get();
            success = true;
        }
        return  success;
    }

    public synchronized boolean doLook(Visible target){
        return true;
    }

    public synchronized boolean doError(String input){
       return true;
    }

}
