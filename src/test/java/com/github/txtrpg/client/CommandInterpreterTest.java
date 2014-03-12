package com.github.txtrpg.client;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionName;
import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.antlr4.CommandLexer;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.*;
import com.github.txtrpg.tasks.ProcessActionTask;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableWithSize.*;
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
        Container<Item> ground = new Container<>();
        ground.put(new Item("a little bottle", "a little bottle", 1));
        ground.put(new Item("old copper coin", "old copper _coin_"));
        ground.put(new Item("silver coin", "silver _coin_"));
        ground.put(new Item("small gold coin", "small gold _coin_"));
        s1.setGround(ground);
        mockActionProcessor = mock(ActionProcessor.class);
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenReturn(mock(OutputStream.class));
        player = new Player("p1", "player", s1, mockActionProcessor, mockSocket);
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
        parseCommand(player, mockActionProcessor, "look");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
    }

    @Test
    public void testError() throws Exception {
        parseCommand(player, mockActionProcessor, "err");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.error)));
    }

    @Test
    public void testLookDisambiguate() throws Exception {
        parseCommand(player, mockActionProcessor, "look co");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.disambiguate)));
        assertThat(actionArgument.getValue(), hasProperty("candidates",
                iterableWithSize(3)));

    }

@Test
    public void testLookDisambiguateIndexOutOfBounds() throws Exception {
        parseCommand(player, mockActionProcessor, "look co 4");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.error)));
    }

    @Test
    public void testLookError() throws Exception {
        parseCommand(player, mockActionProcessor, "look 1");
        ArgumentCaptor<Action> actionArgument = ArgumentCaptor.forClass(Action.class);
        verify(mockActionProcessor, times(1)).addAction(actionArgument.capture());
        assertThat(actionArgument.getValue(), hasProperty("name", equalTo(ActionName.error)));
    }

    private void parseCommand(Player player, ActionProcessor mockActionProcessor, String input) {
        CommandLexer lexer = new CommandLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommandParser parser = new CommandParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        CommandInterpreter interpreter = new CommandInterpreter(player, parser, mockActionProcessor);
        ParseTreeWalker walker = new ParseTreeWalker();
        try {
            walker.walk(interpreter, parser.command());
        } catch (ParseCancellationException e) {
            mockActionProcessor.addAction(new ErrorAction(player, "You cannot do -%s- here", input));
        }
    }
}
