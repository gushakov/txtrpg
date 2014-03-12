package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gushakov
 */
public class ProcessActionTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProcessActionTask.class);

    private Action action;

    public ProcessActionTask(Action action) {
        logger.debug("Created ProcessActionTask {}", this);
        this.action = action;
    }

    @Override
    public void run() {
        logger.debug("Processing action {}", action);
        switch (action.getName()) {
            case disambiguate:
                disambiguate();
                break;
            case welcome:
                welcome();
                break;
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

    //  disambiguate
    private void disambiguate() {
        logger.debug("Disambiguating...");
        action.getInitiator().doDisambiguate(((DisambiguateAction) action).getCandidates());
    }

    // welcome
    private void welcome() {
        logger.debug("Welcoming...");
        action.getInitiator().doWelcome();
    }

    // move
    private void move() {
        logger.debug("Moving...");
        action.getInitiator().doMove(((MoveAction) action).getDir());
    }

    // look
    private void look() {
        logger.debug("Looking...");
        action.getInitiator().doLook(((LookAction) action).getTarget());
    }

    // error
    private void error() {
        logger.debug("Error...");
        action.getInitiator().doError(((ErrorAction) action).getError());
    }

    // quit
    private void quit() {
        logger.debug("Quitting...");
        action.getInitiator().doQuit();
    }
}
