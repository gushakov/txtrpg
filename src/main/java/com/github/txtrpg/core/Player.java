package com.github.txtrpg.core;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.LookAction;
import com.github.txtrpg.utils.ConsoleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author gushakov
 */
public class Player extends Actor {

    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private PrintWriter socketWriter;

    private boolean quit;

    public Player(String name, String description, Scene location, ActionProcessor actionProcessor, Socket socket) {
        super(name, description, location, actionProcessor);
        this.quit = false;
        this.getLocation().getRoom().enter(this);
        try {
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            try {
                socket.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public synchronized boolean isQuit() {
        return quit;
    }

    @Override
    public synchronized boolean doDisambiguate(List<Entity> candidates) {
        sendMessage("There are several of those here:", false, false);
        for (int i = 0; i < candidates.size(); i++) {
            sendMessage("#" + (i + 1) + "#: " + candidates.get(i).getName(), true, false);
        }
        updateStatus();
        return true;
    }

    @Override
    public synchronized boolean doWelcome() {
        sendMessage("+-------------------------------------------+", false, false);
        sendMessage("|             *WELCOME*                       |", true, false);
        sendMessage("+-------------------------------------------+", false, false);
        updateStatus();
        getActionProcessor().addAction(new LookAction(this, getLocation()));
        return true;
    }

    @Override
    public synchronized boolean doMove(Dir dir) {
        boolean moved = super.doMove(dir);
        if (moved) {
            getActionProcessor().addAction(new LookAction(this, getLocation()));
        } else {
            getActionProcessor().addAction(new ErrorAction(this, "You cannot go -%s- from here.", dir.getDirection()));
        }
        return moved;
    }

    @Override
    public synchronized boolean doLook(Visible target) {
        if (target != null) {
            sendMessage(target.getDescription());
        } else {
            sendMessage(getLocation().getDescription());
        }
        return true;
    }

    @Override
    public synchronized boolean doError(String error) {
        if (error != null) {
            sendMessage(error);
        }
        return true;
    }

    @Override
    public synchronized void doQuit() {
        getLocation().getRoom().quit(this);
        socketWriter.write("Bye.");
        socketWriter.flush();
        quit = true;
    }

    public void updateStatus() {
        socketWriter.write(">");
        socketWriter.flush();
    }

    private void sendMessage(String message) {
        sendMessage(message, true, true);
    }

    private void sendMessage(String message, boolean withColor, boolean withStatus) {
        String out;
        if (withColor) {
            out = ConsoleUtils.color(message);
        } else {
            out = message;
        }
        socketWriter.println(out);
        if (withStatus) {
            updateStatus();
        } else {
            socketWriter.flush();
        }
    }

}
