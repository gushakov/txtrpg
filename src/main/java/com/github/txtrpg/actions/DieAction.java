package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Npc;
import com.github.txtrpg.core.Scene;

import java.util.Collection;

/**
 * @author gushakov
 */
public class DieAction extends Action {
    public DieAction(ActionName name, Actor initiator) {
        super(ActionName.die, initiator);
    }

    @Override
    protected void processForNonPlayer(Collection<Action> actions, Actor actor) {
        if (actor instanceof Npc){
            Npc npc = (Npc) actor;
            // remove npc from the scene
            Scene scene = npc.getLocation();
            scene.getRoom().remove(npc);
            // create a corpse

        }
    }
}
