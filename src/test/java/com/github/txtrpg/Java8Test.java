package com.github.txtrpg;

import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Item;
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

}
