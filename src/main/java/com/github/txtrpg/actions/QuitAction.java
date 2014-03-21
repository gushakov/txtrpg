package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.util.Collection;

/**
 * @author gushakov
 */
public class QuitAction extends Action {
    public QuitAction(Actor initiator) {
        super(ActionName.quit, initiator);
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        player.getLocation().getRoom().leave(player);
        player.sendMessage("Bye.");
        player.doQuit();
    }
}
