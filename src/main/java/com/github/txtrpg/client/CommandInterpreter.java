package com.github.txtrpg.client;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.antlr4.CommandBaseListener;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Player;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class CommandInterpreter extends CommandBaseListener {

    private static final Logger logger = LoggerFactory.getLogger(CommandInterpreter.class);

    private Player player;

    private CommandParser parser;

    private ActionProcessor actionProcessor;

    public CommandInterpreter(Player player, CommandParser parser, ActionProcessor actionProcessor) {
        this.player = player;
        this.parser = parser;
        this.actionProcessor = actionProcessor;
    }

    @Override
    public void enterMove(@NotNull CommandParser.MoveContext ctx) {
        actionProcessor.addAction(new MoveAction(player, Dir.valueOf(ctx.getText())));
        super.enterMove(ctx);
    }

    @Override
    public void enterLook(@NotNull CommandParser.LookContext ctx) {
        Action action;
        switch (parser.getVariant()) {
            case 1:
                action = new LookAction(player);
                break;
            case 2:
            case 3:
                String param1 = parser.getParam1();
                String param2 = parser.getParam2();
                List<Item> candidates = player.getLocation().getGround().find(param1);
                if (candidates.size() > 1) {
                    if (param2 != null) {
                        Integer index = Integer.parseInt(parser.getParam2()) - 1;
                        if (index >= 0 && index < candidates.size()) {
                            action = new LookAction(player, candidates.get(index));
                        } else {
                            action = new ErrorAction(player, "There is no -%d- of -%s- here", index, param1);
                        }
                    } else {
                        action = new DisambiguateAction(player,
                                candidates.stream().map(Entity.class::cast).collect(Collectors.toList()));
                    }
                } else if (candidates.size() == 1) {
                    action = new LookAction(player, candidates.get(0));
                } else {
                    action = new ErrorAction(player, "There are no -%s- here", param1);
                }
                break;
            default:
                throw new RuntimeException("Cannot interpret command structure.");
        }
        actionProcessor.addAction(action);
        super.enterLook(ctx);
    }

    @Override
    public void enterQuit(@NotNull CommandParser.QuitContext ctx) {
        QuitAction action = new QuitAction(player);
        actionProcessor.addAction(action);
        super.enterQuit(ctx);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {
        parser.resetParams();
        super.exitEveryRule(ctx);
    }
}
