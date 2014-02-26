package com.github.txtrpg.client;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionName;
import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.client.CommandInterpreter;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author gushakov
 */
public class CommandInterpreterTest {

    private Player player;
    private ActionProcessor mockActionProcessor;
    ArgumentCaptor<Action> actionArgument;

    @Before
    public void setUp() throws Exception {
        player = new Player(mock(PrintWriter.class));
        player.setLocation(new Scene("Forest path"));
        mockActionProcessor = mock(ActionProcessor.class);
        actionArgument = ArgumentCaptor.forClass(Action.class);
    }

    @Test
    public void testMove() throws Exception {
        parseCommand(player, mockActionProcessor, "n");
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), allOf(
                hasProperty("name", equalTo(ActionName.move)),
                hasProperty("dir", equalTo(Dir.n))
        ));
    }

    @Test
    public void testLook() throws Exception {
        parseCommand(player, mockActionProcessor, "look coin");
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), allOf(hasProperty("name", equalTo(ActionName.look)),
                hasProperty("target", notNullValue())));
    }

    private void parseCommand(Player player, ActionProcessor mockActionProcessor, String input) {
        CommandLexer lexer = new CommandLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommandParser parser = new CommandParser(tokens);
        CommandInterpreter interpreter = new CommandInterpreter(player, parser, mockActionProcessor);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(interpreter, parser.command());
    }
}
