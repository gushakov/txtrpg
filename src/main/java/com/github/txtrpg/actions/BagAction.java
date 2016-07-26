package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Player;

import java.util.Collection;

/**
 * @author gushakov
 */
public class BagAction extends Action {
    public BagAction(Actor initiator) {
        super(ActionName.bag, initiator);
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {

        final StringBuilder builder = new StringBuilder("You are carrying:\n\r");

        player.getBag().stream().filter(it -> !it.isLocked())
                .forEach(item -> {
                    builder.append(String.format("%s %10s\n\r", item.getName(), "[#"+item.getWeight()+"#]"));
                });

        player.sendMessage(builder.toString(), true, true);

    }
}
