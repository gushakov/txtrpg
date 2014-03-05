package com.github.txtrpg.core;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.ErrorAction;
import com.github.txtrpg.actions.LookAction;
import com.github.txtrpg.utils.ConsoleUtils;

import java.io.PrintWriter;

/**
 * @author gushakov
 */
public class Player extends Actor {

    private PrintWriter socketWriter;

    public Player(Scene location, ActionProcessor actionProcessor, PrintWriter socketWriter) {
        super(location, actionProcessor);
        this.socketWriter = socketWriter;
    }

    public void sendMessage(String message) {
        socketWriter.println(ConsoleUtils.color(message));
        updateStatus();
    }

    public void updateStatus(){
        socketWriter.write(">");
        socketWriter.flush();
    }

    @Override
    public synchronized boolean doWelcome() {
        socketWriter.println("+-------------------------------------------+");
        socketWriter.println(ConsoleUtils.color("|             *WELCOME*                       |"));
        socketWriter.println("+-------------------------------------------+");
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
            getActionProcessor().addAction(new ErrorAction(this, dir.toString()));
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
    public synchronized boolean doError(String input) {
        if (input != null) {
            sendMessage("You cannot do -" + input + "- here.");
        }
        return true;
    }
}
