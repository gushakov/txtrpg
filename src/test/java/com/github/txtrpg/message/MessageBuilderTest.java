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
                .withColumns(ColumnLayout.list)
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

}
