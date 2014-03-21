package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;

import java.util.Collection;
import java.util.List;

/**
 * @author gushakov
 */
public class DisambiguateAction extends Action {

    private List<Entity> candidates;

    public DisambiguateAction(Actor initiator, List<Entity> candidates) {
        super(ActionName.disambiguate, initiator);
        this.candidates = candidates;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        player.sendMessage("There are several of those here:", false, false);
        for (int i = 0; i < candidates.size(); i++) {
            player.sendMessage("#" + (i + 1) + "#: " + candidates.get(i).getName(), true, false);
        }
        player.updateStatus();
    }

}
