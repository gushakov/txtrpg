package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Visible;

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

    public List<Entity> getCandidates() {
        return candidates;
    }
}
