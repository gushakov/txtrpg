package com.github.txtrpg.core;

import com.github.txtrpg.utils.ConsoleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author gushakov
 */
public class Player extends Actor {

    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private PrintWriter socketWriter;

    private boolean quit;

    public Player(String name, String description, Scene location, Socket socket) {
        super(name, description, location);
        this.quit = false;
        this.getLocation().getRoom().add(this);
        setHealth(new Dice(100).roll());
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

    public synchronized void doQuit() {
        socketWriter.flush();
        quit = true;
    }

    public void updateStatus() {
        socketWriter.write(ConsoleUtils.color("[-\u2661- +" +
                getHealth() +
                "+][\uD83C\uDF00  +23+]") + " >");
        socketWriter.flush();
    }

    public void sendMessage(String message) {
        sendMessage(message, true, true);
    }

    public void sendMessage(String message, boolean withColor, boolean withStatus) {
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
