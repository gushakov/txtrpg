package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public abstract class AbstractCommandProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCommandProcessor.class);
    protected CommandParser parser;
    protected Player player;

    public AbstractCommandProcessor(CommandParser parser, Player player) {
        this.parser = parser;
        this.player = player;
    }

    public Action process() {
        final String param1 = parser.getParam1();
        final String param2 = parser.getParam2();
        final String param3 = parser.getParam3();
        final String param4 = parser.getParam4();
        Integer targetIndex;
        final List<Entity> targetCandidates;
        final List<Entity> contextCandidates;
        switch (parser.getVariant()) {
            case 1:
                logger.debug("Case 1");
                return doProcess(player);
            case 2:
                logger.debug("Case 2: param1: {}", param1);
                // get all candidates
                targetCandidates = getTargetCandidates(param1);

                // need to disambiguate between several targets
                if (targetCandidates.size() > 1) {
                    return new DisambiguateAction(player,
                            targetCandidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                }

                // act upon one thing
                if (targetCandidates.size() == 1) {
                    return doProcess(player, targetCandidates.get(0));
                }

                // no candidates for target
                return new ErrorAction(player,
                        new MessageBuilder("There are no ")
                                .append(param1, Color.red)
                                .append(" here")
                                .toString(), param1);
            case 3:
                logger.debug("Case 3: param1: {}, param2: {}", param1, param2);
                // get all candidates
                targetCandidates = getTargetCandidates(param1);

                // act upon target at specified index
                targetIndex = Integer.parseInt(param2) - 1;
                if (targetIndex >= 0 && targetIndex < targetCandidates.size()) {
                    return doProcess(player, targetCandidates.get(targetIndex));
                }

                // invalid target index
                return new ErrorAction(player, "There is no -%d- of -%s- here", targetIndex, param1);
            case 4:
                logger.debug("Case 4: param1: {}, param3: {}", param1, param3);

                // get all context candidates
                contextCandidates = getContextCandidates(param3);

                // need to disambiguate between several context entities
                if (contextCandidates.size() > 1) {
                    return new DisambiguateAction(player,
                            contextCandidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                }

                // context is determined
                if (contextCandidates.size() == 1) {

                    final Entity contextEntity = contextCandidates.get(0);

                    // get all target candidates for this context
                    targetCandidates = getTargetCandidates(param1, contextEntity);

                    // need to disambiguate between several targets for this context
                    if (targetCandidates.size() > 1) {
                        return new DisambiguateAction(player,
                                targetCandidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                    }

                    // if there is only one target in this context
                    if (targetCandidates.size() == 1) {
                        // act upon the target in this context
                        return doProcess(player, targetCandidates.get(0), contextEntity);
                    }

                    // no candidates for target in this context
                    return new ErrorAction(player, "#%s# does not have -%s-", contextEntity, param1);

                }

                // no candidates for context
                return new ErrorAction(player,
                        new MessageBuilder("There are no ")
                                .append(param1, Color.red)
                                .append(" here")
                                .toString(), param3);

            default:
                throw new RuntimeException("Cannot interpret command structure.");
        }
    }

    protected abstract List<Entity> getTargetCandidates(String prefix);

    protected abstract List<Entity> getTargetCandidates(String prefix, Entity contextEntity);

    protected abstract List<Entity> getContextCandidates(String prefix);

    protected abstract Action doProcess(Player player);

    protected abstract Action doProcess(Player player, Entity targetEntity);

    protected abstract Action doProcess(Player player, Entity targetEntity, Entity contextEntity);
}
