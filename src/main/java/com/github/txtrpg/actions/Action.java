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

    public Action(ActionName name, Actor initiator) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now();
    }

    public Action(ActionName name, Actor initiator, int delay) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now().plus((long) delay, ChronoUnit.SECONDS);
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

    public Collection<Action> process() {
        synchronized (lock) {
            List<Action> actions = new ArrayList<>();
            Actor actor = getInitiator();
            actor.setIdle(false);
            processForActor(actions, actor);
            if (actor instanceof Player) {
                Player player = (Player) actor;
                processForPlayer(actions, player);
            } else {
                processForNonPlayer(actions, actor);
            }
            actor.setIdle(true);
            return actions;
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
                "]";
    }
}
