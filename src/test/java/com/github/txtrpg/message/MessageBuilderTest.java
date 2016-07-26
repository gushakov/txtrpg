package com.github.txtrpg.message;

import org.junit.Test;

import static com.github.txtrpg.message.MessageBuilder.yellow;

/**
 * @author gushakov
 */
public class MessageBuilderTest {

    @Test
    public void testMessageBuilder() throws Exception {
        System.out.println(new MessageBuilder("tree has ")
                .br().append(yellow(), "leaves").build());
    }

}
