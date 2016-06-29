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
    public Action process() {
        Action action;
        final String param1 = parser.getParam1();
        final String param2 = parser.getParam2();
        List<Entity> candidates;
        switch (parser.getVariant()) {
            case 1:
                // simple look
                return new LookAction(player);
            case 2:
                // look at myself
                if (param1.equalsIgnoreCase("me")){
                    return new LookAction(player, player);
                }
                // get all candidates
                candidates = getTargetCandidates(param1);

                // need to disambiguate between several targets
                if (candidates.size() > 1) {
                    return new DisambiguateAction(player,
                            candidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                }

                // look at one thing
                if (candidates.size() == 1){
                    return new LookAction(player, candidates.get(0));
                }

                // no candidates for look
                return new ErrorAction(player, "There are no -%s- here", param1);
            case 3:
                // get all candidates
                candidates = getTargetCandidates(param1);

                // look at target at specified index
                Integer index = Integer.parseInt(param2) - 1;
                if (index >= 0 && index < candidates.size()) {
                    return new LookAction(player, candidates.get(index));
                }

                // invalid index
                return new ErrorAction(player, "There is no -%d- of -%s- here", index, param1);
            default:
                throw new RuntimeException("Cannot interpret command structure.");
        }
    }

    private List<Entity> getTargetCandidates(String prefix) {
        List<Entity> candidates = new ArrayList<>();
        final Scene location = player.getLocation();
        candidates.addAll(location.getGround().find(prefix));
        candidates.addAll(location.getRoom().getOtherMatchingPlayers(player, prefix).collect(Collectors.toList()));
        candidates.addAll(location.getRoom().getOtherMatchingActors(player, prefix).collect(Collectors.toList()));
        return candidates;
    }

}
