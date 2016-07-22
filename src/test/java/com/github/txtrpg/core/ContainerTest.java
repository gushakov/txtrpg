package com.github.txtrpg.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class ContainerTest {

    private static final Logger logger = LoggerFactory.getLogger(ContainerTest.class);

    private static final Random random = new Random(System.currentTimeMillis());

    @Test
    public void testConcurrentTake() throws Exception {

        final Container<Item> chest = new Container<>("chest", "chest");
        chest.put(new Item("coin1", "copper coin"));
        chest.put(new Item("coin2", "silver coin"));
        chest.put(new Item("coin3", "gold coin"));

        final Container<Item> bag = new Container<>("bag", "bag");

        final ExecutorService executor = Executors.newFixedThreadPool(2);
        final Random random = new Random(System.currentTimeMillis());

        Runnable fromBagToChest = () -> {
            transfer(bag, chest);
        };

        Runnable fromChestToBag = () -> {
            transfer(chest, bag);
        };

        for (int i = 0; i < 10; i++) {
            if (random.nextInt(2) == 0) {
                executor.submit(fromChestToBag);
            } else {
                executor.submit(fromBagToChest);
            }
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        logger.debug("Chest: {}, bag: {}", chest.stream().collect(Collectors.toList()), bag.stream().collect(Collectors.toList()));
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(10) * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void transfer(Container<Item> from, Container<Item> to) {
        final List<Item> coins = from.find("coin");
        if (coins.size() > 0) {
            if (coins.stream().anyMatch(Item::isLocked)){
                throw new AssertionError("Found locked item");
            }
            logger.debug("{}: {}", from, coins);
            Collections.shuffle(coins);
            final Item item = coins.get(0);
            sleep();
            item.lock();
            from.remove(item);
            logger.debug("Removed {} from {}", item, from);
            sleep();
            item.unlock();
            sleep();
            item.lock();
            to.put(item);
            logger.debug("Added {} to {}", item, to);
            sleep();
            item.unlock();
        }
        else {
            logger.debug("{} is empty", from);
        }
    }

}
