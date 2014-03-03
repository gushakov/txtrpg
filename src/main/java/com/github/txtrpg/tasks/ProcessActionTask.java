package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionName;
import com.github.txtrpg.actions.LookAction;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;
import com.github.txtrpg.core.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.content.text.plain;

import static com.github.txtrpg.actions.ActionName.*;

/**
 * @author gushakov
 */
public class ProcessActionTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProcessActionTask.class);

    private Action action;

    private World world;

    public ProcessActionTask(World world, Action action) {
        logger.debug("Created ProcessActionTask {}", this);
        this.world = world;
        this.action = action;
    }

    @Override
    public void run() {
        logger.debug("Processing action {}", action);
        switch (action.getName()) {
            case move:
                move();
                break;
            case look:
                look();
                break;
            case quit:
                quit();
                break;
            default:
                logger.error("Unknown action {}", action);
        }
    }

    // move
    private void move() {
        logger.debug("Moving...");
    }

    // look
    private void look() {
        logger.debug("Looking...");
        Visible target = ((LookAction) action).getTarget();
        if (target == null) {
            if (action.getInitiator() instanceof Player) {
                // if initiator is a player output the description of the player's location
                Player initiator = (Player) action.getInitiator();
                logger.debug("Looking at {}", initiator.getLocation().getDescription());
                initiator.sendMessage(initiator.getLocation().getDescription());
            }
        }
    }

    // quit
    private void quit() {
        logger.debug("Quitting...");
        System.exit(0);
    }
}
