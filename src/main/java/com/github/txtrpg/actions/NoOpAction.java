package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

/**
 * @author gushakov
 */
public class NoOpAction extends Action {
    public NoOpAction(Actor initiator) {
        super(ActionName.noop, initiator);
    }
}
