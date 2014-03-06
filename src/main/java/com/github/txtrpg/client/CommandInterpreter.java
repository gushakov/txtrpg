package com.github.txtrpg.client;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.antlr4.CommandBaseListener;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LookAction action = null;
        String param1 = parser.getParam1();
        if (param1 != null) {
            action = new LookAction(player, new Visible() {
                @Override
                public String getDescription() {
                    return param1;
                }
            });
        } else {
            action = new LookAction(player);
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
    public void visitErrorNode(@NotNull ErrorNode node) {
        actionProcessor.addAction(new ErrorAction(player, node.getText()));
        super.visitErrorNode(node);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {
        parser.resetParams();
        super.exitEveryRule(ctx);
    }
}
