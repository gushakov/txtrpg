package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author gushakov
 */
public class MoveAction extends Action {
    private Dir dir;

    public MoveAction(Actor initiator, Dir dir) {
        super(ActionName.move, initiator);
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    @Override
    public synchronized Collection<? extends Action> process() {
        List<Action> result = new ArrayList<>();
        Actor initiator = getInitiator();
        Scene from = initiator.getLocation();
        Optional<Scene> toOpt = from.getExitTo(dir);
        if (toOpt.isPresent()) {
            Scene to = toOpt.get();
            from.getRoom().leave(initiator);
            to.getRoom().enter(initiator);
            initiator.setLocation(to);
            if (initiator instanceof Player) {
                result.add(new LookAction(initiator));
            }
        } else {
            result.add(new ErrorAction(initiator, "You cannot go -%s- from here.", dir.getDirection()));
        }
        return result;
    }
}
