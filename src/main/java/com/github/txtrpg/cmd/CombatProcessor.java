package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.CombatAction;
import com.github.txtrpg.actions.ErrorAction;
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
public class CombatProcessor extends CommandProcessorAdapter {
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
    protected Action doProcess(Player player, Entity targetEntity) {
        if (targetEntity instanceof Actor){
            return new CombatAction(player, (Actor) targetEntity);
        }
        else {
            return new ErrorAction(player, "You cannot attack -%s-", targetEntity.getName());
        }
    }
}
