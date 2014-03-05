package com.github.txtrpg.client;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionName;
import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.tasks.ProcessActionTask;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author gushakov
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommandInterpreterTest.TestConfig.class)
public class CommandInterpreterTest {

    @Configuration
    public static class TestConfig {
    }

    private Player player;
    private ActionProcessor mockActionProcessor;

    @Before
    public void setUp() throws Exception {
        Scene s1 = new Scene("s1", "Forest path");
        Scene s2 = new Scene("s2", "Forest meadow");
        s1.addExit(Dir.n, s2);
        mockActionProcessor = mock(ActionProcessor.class);
        player = new Player("p1", "player", s1, mockActionProcessor, mock(PrintWriter.class));
    }

    @Test
    public void testMove() throws Exception {
        parseCommand(player, mockActionProcessor, "n");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), allOf(
                hasProperty("name", equalTo(ActionName.move)),
                hasProperty("dir", equalTo(Dir.n))
        ));

        ProcessActionTask task = new ProcessActionTask(actionArgument.getValue());
        task.run();
        actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(2)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.look)));
    }

    @Test
    public void testLook() throws Exception {
        parseCommand(player, mockActionProcessor, "look coin");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());

        assertThat(actionArgument.getValue(), allOf(hasProperty("name", equalTo(ActionName.look)),
                hasProperty("target", notNullValue())));
    }

    @Test
    public void testError() throws Exception {
        parseCommand(player, mockActionProcessor, "err");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.error)));
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
