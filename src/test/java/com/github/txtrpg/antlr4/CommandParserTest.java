package com.github.txtrpg.antlr4;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;

import java.io.StringReader;

/**
 * @author gushakov
 */
public class CommandParserTest {

    public void testParse() throws Exception {
        SimpleLexer lexer = new SimpleLexer(new UnbufferedCharStream(new StringReader("n")));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleParser parser = new SimpleParser(tokens);
        parser.move();
    }

}
