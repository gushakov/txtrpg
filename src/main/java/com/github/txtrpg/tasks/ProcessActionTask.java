package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.content.text.plain;

import java.util.Optional;

import static com.github.txtrpg.actions.ActionName.*;

/**
 * @author gushakov
 */
public class ProcessActionTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProcessActionTask.class);

    private ActionProcessor actionProcessor;

    private Action action;

    private World world;

    public ProcessActionTask(World world, Action action, ActionProcessor actionProcessor) {
        logger.debug("Created ProcessActionTask {}", this);
        this.world = world;
        this.action = action;
        this.actionProcessor = actionProcessor;
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
            case error:
                error();
                break;
            default:
                logger.error("Unknown action {}", action);
        }
    }

    // move
    private void move() {
        logger.debug("Moving...");
        Dir dir = ((MoveAction)action).getDir();
        Scene from = action.getInitiator().getLocation();
        Optional<Scene> to = from.getExitTo(dir);
        Actor initiator = action.getInitiator();
        if (to.isPresent()){
            logger.debug("{} is moving from {} to {}", from, to.get());
            initiator.setLocation(to.get());
            LookAction lookAction = new LookAction(initiator);
            actionProcessor.addAction(lookAction);
        } else {
            if (initiator instanceof Player) {
                Player player = (Player) initiator;
                ErrorAction errorAction = new ErrorAction(player);
                errorAction.setInput(dir.toString());
                actionProcessor.addAction(errorAction);
            }
        }
    }

    // look
    private void look() {
        logger.debug("Looking...");
        Visible target = ((LookAction) action).getTarget();
        if (target == null) {
            if (action.getInitiator() instanceof Player) {
                // if initiator is a player output the description of the player's location
                Player player = (Player) action.getInitiator();
                logger.debug("{} is looking at {}", player, player.getLocation());
                player.sendMessage(player.getLocation().getDescription());
            }
        }
    }

    // error
    private void error() {
        logger.debug("Error...");
        Player player = (Player) action.getInitiator();
        String input = ((ErrorAction) action).getInput();
        player.sendMessage("You cannot do -" + input + "- here.");
    }

    // quit
    private void quit() {
        logger.debug("Quitting...");
        System.exit(0);
    }
}
