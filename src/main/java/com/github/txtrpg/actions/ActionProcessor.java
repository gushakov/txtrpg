package com.github.txtrpg.actions;

import com.github.txtrpg.tasks.ProcessActionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author gushakov
 */
public class ActionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ActionProcessor.class);

    private ThreadPoolTaskExecutor actionsTaskExecutor;

    private ConcurrentLinkedQueue<Action> actionsQueue;

    public void setActionsTaskExecutor(ThreadPoolTaskExecutor actionsTaskExecutor) {
        this.actionsTaskExecutor = actionsTaskExecutor;
    }

    @PostConstruct
    public void init() {
        actionsQueue = new ConcurrentLinkedQueue<>();
    }

    public void addAction(Action action) {
        actionsQueue.add(action);
    }

    public void addActions(Collection<? extends Action> actions) {
        actionsQueue.addAll(actions);
    }

    public void processActions(LocalDateTime clock) {
        // start processing next action frame
        boolean done = false;
        while (!done && !actionsQueue.isEmpty()) {
            Action action = actionsQueue.peek();
            // consider action for processing only if it has not started already (from different task or thread)
            if (!action.isStarted() && action.getTime().isBefore(clock)) {
                actionsTaskExecutor.submit(new ProcessActionTask(this, action));
                actionsQueue.remove(action);
            } else {
                done = true;
            }
        }
    }
}
