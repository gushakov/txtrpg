package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.util.Collection;

/**
 * @author gushakov
 */
public class ErrorAction extends Action {

    private String error;

    public ErrorAction(Actor initiator, String template, Object... args) {
        super(ActionName.error, initiator);
        this.error = String.format(template, args);
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        player.sendMessage(error);
    }
}
