package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.CombatAction;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.NoOpAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class CombatProcessor extends CommandProcessor {
    public CombatProcessor(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix) {
        List<Entity> candidates = new ArrayList<>();
        final Scene location = player.getLocation();
        candidates.addAll(location.getRoom().getOtherMatchingActors(player, prefix).collect(Collectors.toList()));
        return candidates;
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
        if (targetEntity instanceof Actor){

            return new CombatAction(player, (Actor) targetEntity);
        }
        else {
            return new ErrorAction(player, "You cannot attack -%s-", targetEntity.getName());
        }
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity, Entity contextEntity) {
        return new NoOpAction(player);
    }

}
