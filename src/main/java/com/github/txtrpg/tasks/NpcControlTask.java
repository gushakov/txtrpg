package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.World;

/**
 * @author gushakov
 */
public class NpcControlTask implements Runnable {

    private World world;

    private ActionProcessor actionProcessor;

    public NpcControlTask(World world, ActionProcessor actionProcessor) {
        this.world = world;
        this.actionProcessor = actionProcessor;
    }

    @Override
    public void run() {
        // TODO:
       // get rooms where spawning is possible
        // create spawn action for each NPC which can be spawned in a room
        // create NPC control action: move or something else
    }
}
