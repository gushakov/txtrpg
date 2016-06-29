package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.npc.NpcController;

/**
 * @author gushakov
 */
public class NpcControlTask implements Runnable {

    private ActionProcessor actionProcessor;

    private NpcController npcController;

    public NpcControlTask(ActionProcessor actionProcessor, NpcController npcController) {
        this.actionProcessor = actionProcessor;
        this.npcController = npcController;
    }

    @Override
    public void run() {
        actionProcessor.addActions(npcController.activate());
        actionProcessor.addActions(npcController.spawn());
    }
}
