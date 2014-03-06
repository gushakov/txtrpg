package com.github.txtrpg.actions;

import com.github.txtrpg.tasks.ProcessActionTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author gushakov
 */
public class ActionProcessor {

    private ThreadPoolTaskExecutor actionsTaskExecutor;

    private ConcurrentLinkedQueue<Action> actionsQueue;

    public void setActionsTaskExecutor(ThreadPoolTaskExecutor actionsTaskExecutor) {
        this.actionsTaskExecutor = actionsTaskExecutor;
    }

    @PostConstruct
    public void init() {
        actionsQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void addAction(Action action) {
        actionsQueue.add(action);
    }

    public synchronized void processActions(LocalDateTime clock) {
        boolean done = false;
        while (!done && !actionsQueue.isEmpty()) {
            Action action = actionsQueue.poll();
            if (action.getTime().compareTo(clock) < 0) {
                actionsTaskExecutor.submit(new ProcessActionTask(action));
            } else {
                done = true;
            }
        }
    }
}
