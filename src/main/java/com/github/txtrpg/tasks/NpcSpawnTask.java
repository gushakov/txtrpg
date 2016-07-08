package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.logic.LogicController;

/**
 * @author gushakov
 */
public class NpcSpawnTask implements Runnable {

    private ActionProcessor actionProcessor;

    private LogicController logicController;

    public NpcSpawnTask(ActionProcessor actionProcessor, LogicController logicController) {
        this.actionProcessor = actionProcessor;
        this.logicController = logicController;
    }

    @Override
    public void run() {
        while(true){

            actionProcessor.addActions(logicController.spawn());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
