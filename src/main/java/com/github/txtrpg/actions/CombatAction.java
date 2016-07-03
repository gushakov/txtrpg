package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dice;
import com.github.txtrpg.core.Player;

import java.util.Collection;

/**
 * @author gushakov
 */
public class CombatAction extends Action {


    private Actor target;

    public CombatAction(Actor initiator, Actor target) {
        super(ActionName.combat, initiator);
        this.target = target;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        int roll = new Dice(3).roll();
        target.decreaseHealth(roll);
        player.sendMessage("You *attack* #" +
                target.getName() +
                "# causing +" +
                roll +
                "+ of damage.");
        if (!target.isAlive()){
            player.sendMessage("\uD83D\uDC80 #" +
                    target.getName() +
                    "# is +dead+.");
        }
    }
}
