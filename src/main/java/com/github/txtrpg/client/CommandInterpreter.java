package com.github.txtrpg.client;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.antlr4.CommandBaseListener;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author gushakov
 */
public class CommandInterpreter extends CommandBaseListener {

    private static final Logger logger  = LoggerFactory.getLogger(CommandInterpreter.class);

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
        logger.debug("Interpreting command: {}", ctx.getText());
        MoveAction action = new MoveAction();
        action.setName(ActionName.move);
        action.setLocation(player.getLocation());
        action.setInitiator(player);
        action.setTime(LocalDateTime.now());
        action.setDir(Dir.valueOf(ctx.getText()));
        actionProcessor.addAction(action);
        logger.debug("Constructed a command action: {}", action);
        super.enterMove(ctx);
    }

    @Override
    public void enterLook(@NotNull CommandParser.LookContext ctx) {
        logger.debug("Interpreting command: {}", ctx.getText());
        LookAction action = new LookAction();
        action.setName(ActionName.look);
        action.setLocation(player.getLocation());
        action.setInitiator(player);
        action.setTime(LocalDateTime.now());
        String param1 = parser.getParam1();
        if (param1 != null) {
            action.setTarget(new Visible() {
                @Override
                public String getDescription() {
                    return param1;
                }
            });
        }
        actionProcessor.addAction(action);
        logger.debug("Constructed a command action: {}", action);
        super.exitLook(ctx);
    }

    @Override
    public void enterQuit(@NotNull CommandParser.QuitContext ctx) {
        logger.debug("Interpreting command: {}", ctx.getText());
        QuitAction action = new QuitAction();
        actionProcessor.addAction(action);
        logger.debug("Constructed a command action: {}", action);
        super.enterQuit(ctx);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {
        parser.resetParams();
        super.exitEveryRule(ctx);
    }
}
