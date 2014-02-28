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

    public Visible getTarget() {
        return target;
    }

    public void setTarget(Visible target) {
        this.target = target;
    }
}
