package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Dice;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.MessageBuilder;

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
        final MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("You attack ")
                .append(target.getName(), Color.cyan)
                .append(" causing ")
                .append(roll, Color.green)
                .append(" of damage");
        if (!target.isAlive()) {
            messageBuilder.br().append(target.getName(), Color.cyan).append(" is dead.");
        }
        player.sendMessage(messageBuilder.toString());
    }
}
