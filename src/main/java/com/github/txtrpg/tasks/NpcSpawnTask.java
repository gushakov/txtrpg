package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.npc.NpcController;

/**
 * @author gushakov
 */
public class NpcSpawnTask implements Runnable {

    private ActionProcessor actionProcessor;

    private NpcController npcController;

    public NpcSpawnTask(ActionProcessor actionProcessor, NpcController npcController) {
        this.actionProcessor = actionProcessor;
        this.npcController = npcController;
    }

    @Override
    public void run() {
        while(true){

        actionProcessor.addActions(npcController.spawn());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
