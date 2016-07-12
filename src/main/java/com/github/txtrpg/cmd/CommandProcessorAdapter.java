package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.NoOpAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;

import java.util.List;

/**
 * @author gushakov
 */
public class CommandProcessorAdapter extends AbstractCommandProcessor {
    public CommandProcessorAdapter(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix) {
        throw new IllegalStateException();
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix, Entity contextEntity) {
        throw new IllegalStateException();
    }

    @Override
    protected List<Entity> getContextCandidates(String prefix) {
        throw new IllegalStateException();
    }

    @Override
    protected Action doProcess(Player player) {
        return new NoOpAction(player);
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity) {
        return new NoOpAction(player);
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity, Entity contextEntity) {
        return new NoOpAction(player);
    }
}
