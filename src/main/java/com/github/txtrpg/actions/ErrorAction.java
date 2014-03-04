package com.github.txtrpg.actions;

import com.github.txtrpg.core.Player;

/**
 * @author gushakov
 */
public class ErrorAction extends Action {

    private String input;

    public ErrorAction(Player player) {
        super(ActionName.error, player);
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
