package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.DisambiguateAction;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.LookAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class LookProcessor extends CommandProcessor {
    public LookProcessor(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected Action doProcess(Player player) {
        return new LookAction(player);
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity) {
        return new LookAction(player, targetEntity);
    }

}
