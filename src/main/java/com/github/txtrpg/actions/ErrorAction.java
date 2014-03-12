package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

/**
 * @author gushakov
 */
public class ErrorAction extends Action {

    private String error;

    public ErrorAction(Actor initiator, String template, Object... args) {
        super(ActionName.error, initiator);
        this.error = String.format(template, args);
    }

    public String getError() {
        return error;
    }
}
