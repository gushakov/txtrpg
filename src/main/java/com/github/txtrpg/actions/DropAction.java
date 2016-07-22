package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Player;
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
        if (item != null){
            try {
                player.getBag().remove(item);
                player.getLocation().getGround().put(item);
                player.sendMessage("You dropped _" + item + "_ to the ground.");
            } finally {
                item.unlock();
            }
        }
        else {
            player.sendMessage("Drop -what-?");
        }
    }
}
