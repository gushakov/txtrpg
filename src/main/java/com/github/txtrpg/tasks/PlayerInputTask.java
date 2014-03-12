package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.WelcomeAction;
import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.client.CommandInterpreter;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.World;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author gushakov
 */
public class PlayerInputTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlayerInputTask.class);
    private Socket socket;
    private ActionProcessor actionProcessor;
    private World world;

    private static final String[] names = {
            "Faervel", "Falasson", "Thavron", "Talathon", "Eglanor"
    };

    public PlayerInputTask(World world, ActionProcessor actionProcessor, Socket socket) {
        this.world = world;
        this.actionProcessor = actionProcessor;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Putty telnet sends in in ISO-8859-1 bytes
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
            String playerName = names[socket.getPort() % names.length];
            Player player = new Player(playerName, "@" + playerName + "@ is standing here.",
                    world.getScenes().get("s1"), actionProcessor, socket);
            world.addPlayer(player);
            actionProcessor.addAction(new WelcomeAction(player));
            String rawInput;
            while ((rawInput = socketReader.readLine()) != null) {
                if (player.isQuit()) {
                    break;
                }
                // windows telnet sends a control character for the first line
                if (rawInput.length() > 0 && rawInput.codePointAt(0) != 255) {
                    // convert input to UTF-8
                    String line = new String(rawInput.getBytes("ISO-8859-1"), "UTF-8");
                    // get rid of all control characters
                    line = line.replaceAll("[\u0000-\u001f]", "");
                    logger.debug("Line to parse: {}", line);
                    CommandLexer lexer = new CommandLexer(new ANTLRInputStream(line));
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    CommandParser parser = new CommandParser(tokens);
                    parser.setErrorHandler(new BailErrorStrategy());
                    CommandInterpreter interpreter = new CommandInterpreter(player, parser, actionProcessor);
                    ParseTreeWalker walker = new ParseTreeWalker();
                    try {
                        walker.walk(interpreter, parser.command());
                    } catch (ParseCancellationException e) {
                        actionProcessor.addAction(new ErrorAction(player, "You cannot do -%s- here", line));
                    }
                }
                player.updateStatus();
            }
            socket.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }
}
