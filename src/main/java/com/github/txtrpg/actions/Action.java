package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gushakov
 */
public class Action {

    private ActionName name;

    private Actor initiator;

    private LocalDateTime time;

    public Action(ActionName name, Actor initiator) {
        this.name = name;
        this.initiator = initiator;
        this.time = LocalDateTime.now();
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

    public synchronized Collection<Action> process() {
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

    protected void processForActor(Collection<Action> actions, Actor actor) {
    }

    protected void processForPlayer(Collection<Action> actions, Player player) {
    }

    protected void processForNonPlayer(Collection<Action> actions, Actor actor) {
    }

}
