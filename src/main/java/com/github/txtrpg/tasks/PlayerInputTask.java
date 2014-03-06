package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.client.CommandInterpreter;
import com.github.txtrpg.core.Player;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author gushakov
 */
public class PlayerInputTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlayerInputTask.class);
    private Player player;
    private String rawInput;
    private ActionProcessor actionProcessor;

    public PlayerInputTask(Player player, String rawInput, ActionProcessor actionProcessor) {
        this.player = player;
        this.rawInput = rawInput;
        this.actionProcessor = actionProcessor;
    }

    @Override
    public void run() {
        try {
            if (rawInput.codePointAt(0) != 255) {
                // convert input to UTF-8
                String line = new String(rawInput.getBytes("ISO-8859-1"), "UTF-8");
                // get rid of all control characters
                line = line.replaceAll("[\u0000-\u001f]", "");
                logger.debug("Line to parse: {}", line);
                CommandLexer lexer = new CommandLexer(new ANTLRInputStream(line));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CommandParser parser = new CommandParser(tokens);
                CommandInterpreter interpreter = new CommandInterpreter(player, parser, actionProcessor);
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(interpreter, parser.command());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
