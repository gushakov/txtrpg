package com.github.txtrpg.core;

import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.MessageBuilder;
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

    private Bag bag;

    public Player(String name, String description, Scene location, Socket socket) {
        super(name, description, location);
        this.quit = false;
        this.getLocation().getRoom().add(this);
        setHealth(70 + new Dice(30).roll());
        bag = new Bag("bag of " + name, "A simple leather bag", 10);
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
        socketWriter.write(new MessageBuilder()
                .append("[")
                .append("H:", Color.red)
                .append(getHealth(), Color.green)
                .append("]")
                .append("M:", Color.cyan)
                .append(23, Color.green)
                .append("]>")
                .toString());
        socketWriter.flush();
    }

    public void sendMessage(String message) {
        sendMessage(message, true);
    }

    public void sendMessage(String message, boolean withStatus) {
        socketWriter.println(message);
        if (withStatus) {
            updateStatus();
        } else {
            socketWriter.flush();
        }
    }

    public synchronized Bag getBag(){
        return bag;
    }

}
