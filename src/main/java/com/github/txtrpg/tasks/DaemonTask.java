package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gushakov
 */
public class DaemonTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DaemonTask.class);

    private ActionProcessor actionProcessor;

    public DaemonTask(ActionProcessor actionProcessor) {
        logger.debug("Created new DaemonTask {}", this);
        this.actionProcessor = actionProcessor;
    }

    @Override
    public void run() {
        actionProcessor.processActions();
    }
}
