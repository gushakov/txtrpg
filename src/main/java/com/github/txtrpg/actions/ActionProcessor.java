package com.github.txtrpg.actions;

import com.github.txtrpg.tasks.ProcessActionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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

    public void processActions() {
        while (true) {
            if (actionsQueue.isEmpty()){
                pause();
                continue;
            }
            final LocalDateTime clock = LocalDateTime.now();
            Action action = actionsQueue.peek();
            // only process actions which are not delayed
            if (action.getTime().isBefore(clock)) {
                actionsTaskExecutor.submit(new ProcessActionTask(this, action));
                actionsQueue.remove(action);
            }
            // done with this iteration, all other actions in the queue will be processed later
            pause();
        }
    }

    private void pause(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
