package com.github.txtrpg.actions;

import com.github.txtrpg.core.Visible;

/**
 * @author gushakov
 */
public class LookAction extends Action {

    private Visible target;

    public LookAction() {
        super(ActionName.look);
    }

    public Visible getTarget() {
        return target;
    }

    public void setTarget(Visible target) {
        this.target = target;
    }
}
