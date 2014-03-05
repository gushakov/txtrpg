package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

/**
 * @author gushakov
 */
public class WelcomeAction extends Action {

    public WelcomeAction(Actor initiator) {
        super(ActionName.welcome, initiator);
    }
}
