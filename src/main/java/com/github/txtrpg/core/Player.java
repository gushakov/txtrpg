package com.github.txtrpg.core;

import com.github.txtrpg.utils.ConsoleUtils;

import java.io.PrintWriter;

/**
 * @author gushakov
 */
public class Player extends Actor {

    private PrintWriter socketWriter;

    public Player(PrintWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void sendMessage(String message) {
        socketWriter.println(ConsoleUtils.color(message));
    }

    public void updateStatus(){
        socketWriter.write(">");
        socketWriter.flush();
    }
}
