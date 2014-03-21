package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;

import java.util.Collection;
import java.util.Collections;

/**
 * @author gushakov
 */
public class LookAction extends Action {

    private Visible target;

    public LookAction(Actor initiator) {
        super(ActionName.look, initiator);
    }

    public LookAction(Actor initiator, Visible target) {
        super(ActionName.look, initiator);
        this.target = target;
    }

    @Override
    public synchronized Collection<Action> process() {
        Actor actor = getInitiator();
        if (actor instanceof Player) {
            Player player = (Player) actor;
            if (target != null) {
                player.sendMessage(target.getDescription());
            } else {
                player.sendMessage(player.getLocation().getDescription());
                player.getLocation().showTo(player).stream()
                        .forEach(v -> player.sendMessage(v.getDescription()));
            }
        }
        return Collections.emptyList();
    }
}
