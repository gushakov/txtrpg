package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author gushakov
 */
public abstract class Action {
    protected final Object lock = new Object();

    private ActionName name;

    private Actor initiator;

    private LocalDateTime time;

    private boolean started;

    public Action(ActionName name, Actor initiator) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now();
        this.started = false;
    }

    public Action(ActionName name, Actor initiator, int delay) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now().plus((long) delay, ChronoUnit.SECONDS);
        this.started = false;
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

    public boolean isStarted(){
        synchronized (lock){
            return started;
        }
    }

    public Collection<Action> process() {
        synchronized (lock){
            if (!started){
                started = true;
                List<Action> actions = new ArrayList<>();
                Actor actor = getInitiator();
                processForActor(actions, actor);
                if (actor instanceof Player) {
                    Player player = (Player) actor;
                    processForPlayer(actions, player);
                } else {
                    processForNonPlayer(actions, actor);
                }
                return actions;
            }
            return Collections.emptyList();
        }
    }

    protected void processForActor(Collection<Action> actions, Actor actor) {
    }

    protected void processForPlayer(Collection<Action> actions, Player player) {
    }

    protected void processForNonPlayer(Collection<Action> actions, Actor actor) {
    }

    @Override
    public String toString() {
        return "[" +
                name +
                ": " +
                initiator +
                ", started: " +
                started +
                "]";
    }
}
