package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.DropAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class DropProcessor extends CommandProcessorAdapter {

    public DropProcessor(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix) {
        return player.getBag().find(prefix).stream()
                .map(Entity.class::cast).collect(Collectors.toList());
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity) {
        return new DropAction(player, (Item) targetEntity);
    }
}
