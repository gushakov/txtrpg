package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.BagAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Player;

/**
 * @author gushakov
 */
public class BagProcessor extends CommandProcessorAdapter {
    public BagProcessor(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected Action doProcess(Player player) {
        return new BagAction(player);
    }
}
