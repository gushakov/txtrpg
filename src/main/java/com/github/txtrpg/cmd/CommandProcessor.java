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
public abstract class CommandProcessor {

    protected CommandParser parser;
    protected Player player;

    public CommandProcessor(CommandParser parser, Player player) {
        this.parser = parser;
        this.player = player;
    }

    public Action process(){
        final String param1 = parser.getParam1();
        final String param2 = parser.getParam2();
        List<Entity> candidates;
        switch (parser.getVariant()) {
            case 1:
                return doProcess(player);
            case 2:
                // get all candidates
                candidates = getTargetCandidates(param1);

                // need to disambiguate between several targets
                if (candidates.size() > 1) {
                    return new DisambiguateAction(player,
                            candidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                }

                // look at one thing
                if (candidates.size() == 1){
                   return doProcess(player, candidates.get(0));
                }

                // no candidates for look
                return new ErrorAction(player, "There are no -%s- here", param1);
            case 3:
                // get all candidates
                candidates = getTargetCandidates(param1);

                // look at target at specified index
                Integer index = Integer.parseInt(param2) - 1;
                if (index >= 0 && index < candidates.size()) {
                    return doProcess(player, candidates.get(index));
                }

                // invalid index
                return new ErrorAction(player, "There is no -%d- of -%s- here", index, param1);
            default:
                throw new RuntimeException("Cannot interpret command structure.");
        }
    }

    protected abstract Action doProcess(Player player);

    protected abstract Action doProcess(Player player, Entity targetEntity);

    protected List<Entity> getTargetCandidates(String prefix) {
        List<Entity> candidates = new ArrayList<>();
        final Scene location = player.getLocation();
        candidates.addAll(location.getGround().find(prefix));
//        candidates.addAll(location.getRoom().getOtherMatchingPlayers(player, prefix).collect(Collectors.toList()));
        candidates.addAll(location.getRoom().getOtherMatchingActors(player, prefix).collect(Collectors.toList()));
        return candidates;
    }
}
