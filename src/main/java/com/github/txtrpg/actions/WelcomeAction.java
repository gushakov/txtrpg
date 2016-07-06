package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gushakov
 */
public class WelcomeAction extends Action {

    public WelcomeAction(Actor initiator) {
        super(ActionName.welcome, initiator);
    }

    @Override
    public Collection<Action> process() {
        synchronized (lock){
            List<Action> actions = new ArrayList<>();
            Actor actor = getInitiator();
            if (actor instanceof Player) {
                Player player = (Player) actor;
                player.sendMessage("+-------------------------------------------+", false, false);
                player.sendMessage("|             *WELCOME*                       |", true, false);
                player.sendMessage("+-------------------------------------------+", false, false);

                actions.add(new LookAction(player));
            }
            actor.getLocation().getRoom().getOtherActors(actor).forEach(a -> actions.add(new NoticeAction(a, actor)));
            return actions;
        }
    }
}
