package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.NoOpAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.Collections;

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
        final LocalDateTime now = LocalDateTime.now();
        actionProcessor.processActions(now);
    }
}
