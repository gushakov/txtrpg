package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

/**
 * @author gushakov
 */
public class ErrorAction extends Action {

    private String input;

    public ErrorAction(Actor initiator, String input) {
        super(ActionName.error, initiator);
        this.input = input;
    }

    public String getInput() {
        return input;
    }

}
