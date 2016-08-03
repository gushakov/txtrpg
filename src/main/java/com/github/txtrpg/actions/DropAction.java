package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.MessageBuilder;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author gushakov
 */
public class DropAction extends Action {

    private Item item;

    public DropAction(ActionName name, Actor initiator) {
        super(name, initiator);
    }

    public DropAction(Actor initiator, Item item) {
        super(ActionName.drop, initiator);
        Assert.notNull(item);
        item.lock();
        this.item = item;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        final MessageBuilder messageBuilder = new MessageBuilder();
        if (item != null) {
            try {
                player.getBag().remove(item);
                player.getLocation().getGround().put(item);
                player.sendMessage(messageBuilder.append("You dropped ")
                        .append(item.toString(), Color.yellow)
                        .append(" to the ground").toString());
            } finally {
                item.unlock();
            }
        } else {
            player.sendMessage(messageBuilder.append("Drop ").append("what", Color.red).append("?").toString());
        }
    }
}
