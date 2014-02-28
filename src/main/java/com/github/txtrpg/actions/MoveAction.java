package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dir;

/**
 * @author gushakov
 */
public class MoveAction extends Action {
    private Dir dir;

    public MoveAction(Actor initiator) {
        super(ActionName.move, initiator);
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
