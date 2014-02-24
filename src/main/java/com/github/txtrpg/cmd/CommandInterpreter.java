package com.github.txtrpg.cmd;

import com.github.txtrpg.antlr4.CommandBaseListener;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Action;
import com.github.txtrpg.core.Player;
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

    private CommandParser parser;

    private Player player;

    private Action action;

    public CommandInterpreter(CommandParser parser, Player player) {
        this.parser = parser;
        this.player = player;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public void enterLook(@NotNull CommandParser.LookContext ctx) {
        logger.debug("Interpreting look command: {}", ctx.getText());
        action = new Action();
        action.setName("look");
        action.setLocation(player.getLocation());
        action.setInitiator(player);
        action.setTime(LocalDateTime.now());
        logger.debug("Constructed action for look command: {}", action);
        super.exitLook(ctx);
    }

    @Override
    public void enterEveryRule(@NotNull ParserRuleContext ctx) {
        action = null;
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {
        parser.resetParams();
        super.exitEveryRule(ctx);
    }
}
