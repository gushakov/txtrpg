package com.github.txtrpg.actions;

import com.github.txtrpg.core.Dir;

/**
 * @author gushakov
 */
public class MoveAction extends Action {
    private Dir dir;

    public MoveAction() {
        super(ActionName.move);
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
