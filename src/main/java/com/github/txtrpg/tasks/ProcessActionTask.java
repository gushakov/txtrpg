package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author gushakov
 */
public class ProcessActionTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProcessActionTask.class);

    private Action action;

    public ProcessActionTask(Action action) {
        logger.debug("Created ProcessActionTask {}", this);
        this.action = action;
    }

    @Override
    public void run() {
        logger.debug("Processing action {}", action);
        action.process();
    }

}
