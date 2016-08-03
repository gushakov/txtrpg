package com.github.txtrpg.message;

import org.junit.Test;

/**
 * @author gushakov
 */
public class MessageBuilderTest {

    @Test
    public void testColor() throws Exception {
        System.out.println(new MessageBuilder().append("foobar", Color.yellow));
    }

    @Test
    public void testMessageBuilder() throws Exception {
        String message = new MessageBuilder()
                .append("You see:")
                .withColumns(ColumnLayout.Double)
                .append("1.")
                .tab()
                .append("bar")
                .tab()
                .append("2.")
                .tab()
                .append("baz")
                .tab()
                .append("3.")
                .tab()
                .append("wam")
                .end()
                .toString();
        System.out.println(message);
    }

    @Test
    public void testParse() throws Exception {
//        String message = new MessageBuilder().parse("This a _test_, this is a test.").toString();
//        System.out.println(message);
        String message = new MessageBuilder().parse("A small yellow #butterfly# flutters around aimlessly.").toString();
        System.out.println(message);
    }

}
