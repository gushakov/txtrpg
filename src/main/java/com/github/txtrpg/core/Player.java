package com.github.txtrpg.core;

import java.io.PrintWriter;

/**
 * @author gushakov
 */
public class Player {
    private PrintWriter socketWriter;

    public Player(PrintWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void sendMessage(String message) {
        socketWriter.println(message);
    }

    public void updateStatus(){
        socketWriter.write(">");
        socketWriter.flush();
    }
}
