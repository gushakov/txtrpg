package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.npc.NpcController;

/**
 * @author gushakov
 */
public class NpcActivateTask implements Runnable {

    private ActionProcessor actionProcessor;

    private NpcController npcController;

    public NpcActivateTask(ActionProcessor actionProcessor, NpcController npcController) {
        this.actionProcessor = actionProcessor;
        this.npcController = npcController;
    }

    @Override
    public void run() {
        while(true){

        actionProcessor.addActions(npcController.activate());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
