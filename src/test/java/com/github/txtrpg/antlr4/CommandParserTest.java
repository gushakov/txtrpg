package com.github.txtrpg.antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

/**
 * @author gushakov
 */
public class CommandParserTest {

    public class CommandListener extends SimpleBaseListener {

        @Override
        public void enterCommand(@NotNull SimpleParser.CommandContext ctx) {
            System.out.println(ctx.getText());
            super.enterCommand(ctx);
        }
    }

    @Test
    public void testParse() throws Exception {
        SimpleLexer lexer = new SimpleLexer(new ANTLRInputStream("look"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleParser parser = new SimpleParser(tokens);

        ParseTreeWalker walker = new ParseTreeWalker();

       walker.walk(new CommandListener(), parser.command());




    }

}
