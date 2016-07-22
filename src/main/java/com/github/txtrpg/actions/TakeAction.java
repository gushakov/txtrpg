package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Container;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Player;

import java.util.Collection;

/**
 * @author gushakov
 */
public class TakeAction extends Action {

    private Item item;

    private Container<Item> container;

    public TakeAction(Actor initiator, Item item) {
        super(ActionName.take, initiator);
        item.lock();
        this.item = item;
    }

    public TakeAction(Actor initiator, Item item, Container<Item> container) {
        super(ActionName.take, initiator);
        item.lock();
        this.item = item;
        this.container = container;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        try {
            if (container == null){
                player.getLocation().getGround().remove(item);
                player.getBag().put(item);
                player.sendMessage("You pick up _" + item.getName() + "_ from the ground.");
            }
            else {
                container.remove(item);
                player.getBag().put(item);
                player.sendMessage("You take _" + item.getName() + "_ from " + container.getName() + ".");
            }
        } finally {
            item.unlock();
        }
    }
}
