package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Npc;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.npc.NpcController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author gushakov
 */
public class SpawnAction extends Action {
    private static final Logger logger = LoggerFactory.getLogger(SpawnAction.class);

    private NpcController npcController;

    public SpawnAction(Npc npc, NpcController npcController) {
        super(ActionName.spawn, npc);
        this.npcController = npcController;
    }

    @Override
    protected void processForActor(Collection<Action> actions, Actor actor) {
        Npc npc = (Npc) getInitiator();
        Scene location = npc.getLocation();
        logger.debug("Spawning NPC: {} at location {}", npc, location);
        location.getRoom().enter(npc);
        actions.addAll(npcController.nextActions(npc));
    }
}
