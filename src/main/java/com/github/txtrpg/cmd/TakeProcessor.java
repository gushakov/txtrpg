package com.github.txtrpg.cmd;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.NoOpAction;
import com.github.txtrpg.actions.TakeAction;
import com.github.txtrpg.antlr4.CommandParser;
import com.github.txtrpg.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gushakov
 */
public class TakeProcessor extends CommandProcessor {
    public TakeProcessor(CommandParser parser, Player player) {
        super(parser, player);
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix) {
        List<Entity> candidates = new ArrayList<>();
        final Scene location = player.getLocation();
        candidates.addAll(location.getGround().find(prefix));
        return candidates;
    }

    @Override
    protected List<Entity> getTargetCandidates(String prefix, Entity contextEntity) {
        List<Entity> candidates = new ArrayList<>();
        if (contextEntity instanceof Container){
            Container<?> container = (Container<?>)contextEntity;
           candidates.addAll(container.find(prefix));
        }
        return candidates;
    }

    @Override
    protected List<Entity> getContextCandidates(String prefix) {
        List<Entity> candidates = new ArrayList<>();
        final Scene location = player.getLocation();
        candidates.addAll(location.getGround().find(prefix));
        return candidates;
    }

    @Override
    protected Action doProcess(Player player) {
        return new ErrorAction(player, "Take -what-?");
    }

    @Override
    protected Action doProcess(Player player, Entity targetEntity) {
        return new TakeAction(player, (Item) targetEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Action doProcess(Player player, Entity targetEntity, Entity contextEntity) {
        return new TakeAction(player, (Item) targetEntity, (Container<Item>) contextEntity);
    }
}
