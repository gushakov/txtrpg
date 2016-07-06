package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Corpse;
import com.github.txtrpg.core.Npc;
import com.github.txtrpg.core.Scene;

import java.util.Collection;

/**
 * @author gushakov
 */
public class DieAction extends Action {
    private Corpse corpse;

    public DieAction(Actor initiator, Corpse corpse) {
        super(ActionName.die, initiator);
        this.corpse = corpse;
    }

    @Override
    protected void processForNonPlayer(Collection<Action> actions, Actor actor) {
        if (actor instanceof Npc){
            Npc npc = (Npc) actor;
            // remove npc from the scene
            Scene scene = npc.getLocation();
            scene.getRoom().remove(npc);
            // add corpse to the ground
            scene.getGround().put(corpse);
            System.out.println(">>>>>>"+scene+">>>>>>>"+scene.getGround().stream().count());

        }
    }
}
