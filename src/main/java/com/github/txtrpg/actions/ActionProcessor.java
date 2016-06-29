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

    public synchronized void addAction(Action action) {
        actionsQueue.add(action);
    }

    public synchronized void addActions(Collection<? extends Action> actions) {
        actionsQueue.addAll(actions);
    }

    public synchronized void processActions(LocalDateTime clock) {
        int counter = 0;
        // start processing next action frame
        final long frameMillis = clock.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();
//        logger.debug("START frame: [{}], queue size: {}", frameMillis, actionsQueue.size());
        boolean done = false;
        while (!done && !actionsQueue.isEmpty()) {
            Action action = actionsQueue.peek();
            if (action.getTime().isBefore(clock)) {
                actionsTaskExecutor.submit(new ProcessActionTask(this, action));
                actionsQueue.poll();
                counter++;
            } else {
                done = true;
            }
        }
//        logger.debug("END frame: [{}], queue size: {}, processed: {}", frameMillis, actionsQueue.size(), counter);
    }
}
