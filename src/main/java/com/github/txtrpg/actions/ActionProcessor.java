package com.github.txtrpg.actions;

import com.github.txtrpg.core.World;
import com.github.txtrpg.tasks.ProcessActionTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author gushakov
 */
@Component
public class ActionProcessor {

    @Autowired
    private ThreadPoolTaskExecutor actionsTaskExecutor;

    private ConcurrentLinkedQueue<Action> actionsQueue;

    public void setActionsTaskExecutor(ThreadPoolTaskExecutor actionsTaskExecutor) {
        this.actionsTaskExecutor = actionsTaskExecutor;
    }

    @PostConstruct
    public void init(){
        actionsQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void addAction(Action action){
        actionsQueue.add(action);
    }

    public synchronized void processActions(World world, LocalDateTime clock){
        boolean done = false;
        while (!done && !actionsQueue.isEmpty()){
            Action action = actionsQueue.poll();
            if (action.getTime().compareTo(clock) < 0){
                actionsTaskExecutor.submit(new ProcessActionTask(world, action));
            }
            else {
                done = true;
            }
        }
    }
}
