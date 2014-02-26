package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author gushakov
 */
public class DaemonTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DaemonTask.class);

    private World world;
    private ActionProcessor actionProcessor;

    public DaemonTask(World world, ActionProcessor actionProcessor) {
        logger.debug("Created new DaemonTask {}", this);
        this.world = world;
        this.actionProcessor = actionProcessor;
    }

    @Override
    public void run() {
           actionProcessor.processActions(world, LocalDateTime.now());
    }
}
