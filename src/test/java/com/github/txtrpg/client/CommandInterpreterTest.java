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
import com.github.txtrpg.core.World;
import com.github.txtrpg.tasks.ProcessActionTask;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommandInterpreterTest.TestConfig.class)
public class CommandInterpreterTest {

    @Configuration
    public static class TestConfig {
    }

    private World world;
    private Player player;
    private ActionProcessor mockActionProcessor;
    private ArgumentCaptor<Action> actionArgument;

    @Before
    public void setUp() throws Exception {
        world = mock(World.class);
        Scene s1 = new Scene("s1", "Forest path");
        Scene s2 = new Scene("s2", "Forest meadow");
        s1.addExit(Dir.n, s2);
        player = new Player(mock(PrintWriter.class));
        player.setLocation(s1);
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

        ProcessActionTask task = new ProcessActionTask(world, actionArgument.getValue());
        task.run();
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
