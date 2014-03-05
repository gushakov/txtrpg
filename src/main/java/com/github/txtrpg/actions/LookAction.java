package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Visible;

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

    public Visible getTarget() {
        return target;
    }

}
