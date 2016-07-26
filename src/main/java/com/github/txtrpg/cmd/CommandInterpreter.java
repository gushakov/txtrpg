package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.antlr4.CommandBaseListener;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
        actionProcessor.addAction(new LookProcessor(parser, player).process());
        super.enterLook(ctx);
    }

    @Override
    public void enterQuit(@NotNull CommandParser.QuitContext ctx) {
        QuitAction action = new QuitAction(player);
        actionProcessor.addAction(action);
        super.enterQuit(ctx);
    }

    @Override
    public void enterAttack(@NotNull CommandParser.AttackContext ctx) {
        actionProcessor.addAction(new CombatProcessor(parser, player).process());
        super.enterAttack(ctx);
    }

    @Override
    public void enterTake(@NotNull CommandParser.TakeContext ctx) {
        actionProcessor.addAction(new TakeProcessor(parser, player).process());
        super.enterTake(ctx);
    }

    @Override
    public void enterDrop(@NotNull CommandParser.DropContext ctx) {
        actionProcessor.addAction(new DropProcessor(parser, player).process());
        super.enterDrop(ctx);
    }

    @Override
    public void enterBag(@NotNull CommandParser.BagContext ctx) {
        actionProcessor.addAction(new BagProcessor(parser, player).process());
        super.enterBag(ctx);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx) {
        parser.resetParams();
        super.exitEveryRule(ctx);
    }
}
