package com.github.txtrpg.cmd;

import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author gushakov
 */
public class CommandInterpreterTest {

    @Test
    public void testLook() throws Exception {
        Player player = new Player(Mockito.mock(PrintWriter.class));
        player.setLocation(new Scene("Forest path"));

        CommandLexer lexer = new CommandLexer(new ANTLRInputStream("look coin"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommandParser parser = new CommandParser(tokens);

        CommandInterpreter interpreter = new CommandInterpreter(parser, player);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(interpreter, parser.command());

        assertThat(interpreter.getAction(), notNullValue());
        assertThat(interpreter.getAction().getInitiator(), equalTo(player));
        assertThat(interpreter.getAction().getLocation().getName(), equalTo("Forest path"));
    }
}
