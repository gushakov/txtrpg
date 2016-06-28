package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author gushakov
 */
public class MoveAction extends Action {
    private static final Logger logger = LoggerFactory.getLogger(MoveAction.class);

    private Dir dir;

    public MoveAction(Actor initiator, Dir dir) {
        super(ActionName.move, initiator);
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    @Override
    protected void processForActor(Collection<Action> actions, Actor actor) {
        Scene from = actor.getLocation();
        Optional<Scene> toOpt = from.getExitTo(dir);
        if (toOpt.isPresent()) {
            Scene to = toOpt.get();
            logger.debug("Moving {} from {} to {}", actor, from, to);
            from.getRoom().leave(actor);
            to.getRoom().enter(actor);
            actor.setLocation(to);

        } else {
            actions.add(new ErrorAction(actor, "You cannot go -%s- from here.", dir.getDirection()));
        }
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        actions.add(new LookAction(player));
    }
}
