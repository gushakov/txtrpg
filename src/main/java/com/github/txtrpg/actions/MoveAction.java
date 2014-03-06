package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dir;

/**
 * @author gushakov
 */
public class MoveAction extends Action {
    private Dir dir;

    public MoveAction(Actor initiator, Dir dir) {
        super(ActionName.move, initiator);
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

}
