package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.logic.LogicController;

/**
 * @author gushakov
 */
public class NpcActivateTask implements Runnable {

    private ActionProcessor actionProcessor;

    private LogicController logicController;

    public NpcActivateTask(ActionProcessor actionProcessor, LogicController logicController) {
        this.actionProcessor = actionProcessor;
        this.logicController = logicController;
    }

    @Override
    public void run() {
        while(true){

            actionProcessor.addActions(logicController.activate());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
