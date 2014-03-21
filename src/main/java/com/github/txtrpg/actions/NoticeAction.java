package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;

import java.util.Collection;

/**
 * @author gushakov
 */
public class NoticeAction extends Action {

    private Visible visible;

    public NoticeAction(Actor initiator, Visible visible) {
        super(ActionName.notice, initiator);
        this.visible = visible;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        player.sendMessage(visible.getDescription());
    }
}
