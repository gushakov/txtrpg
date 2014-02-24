package com.github.txtrpg.core;

import com.github.txtrpg.utils.ConsoleUtils;

import java.io.PrintWriter;

/**
 * @author gushakov
 */
public class Player implements Actor {

    private Scene location;

    private PrintWriter socketWriter;

    public Player(PrintWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public Scene getLocation() {
        return location;
    }

    public void setLocation(Scene location) {
        this.location = location;
    }

    public void sendMessage(String message) {
        socketWriter.println(ConsoleUtils.color(message));
    }

    public void updateStatus(){
        socketWriter.write(">");
        socketWriter.flush();
    }
}
