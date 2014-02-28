package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

/**
 * @author gushakov
 */
public class QuitAction extends Action {
    public QuitAction(Actor initiator) {
        super(ActionName.quit, initiator);
    }
}
