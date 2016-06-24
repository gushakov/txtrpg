package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Player;

/**
 * @author gushakov
 */
public abstract class CommandProcessor {

    protected CommandParser parser;
    protected Player player;

    public CommandProcessor(CommandParser parser, Player player) {
        this.parser = parser;
        this.player = player;
    }

    public abstract Action process();
}
