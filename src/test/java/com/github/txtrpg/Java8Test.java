package com.github.txtrpg;

import com.github.txtrpg.core.Dice;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gushakov
 */
public class Java8Test {

    @Test
    public void testCastEachInStream() throws Exception {
        final List<Entity> entities = Stream.of(new Item("coin", "gold coin"),
                new Item("ring", " ring with a ruby stone"))
                .map(Entity.class::cast)
                .collect(Collectors.toList());
    }

    @Test
    public void testSuccess() throws Exception {
        Dice dice = new Dice("1,2,3,4,5,6/6");
        Assert.assertTrue(dice.success());

    }

    @Test
    public void testCodePoint() throws Exception {
        System.out.println("\n".codePointAt(0));
    }
}
