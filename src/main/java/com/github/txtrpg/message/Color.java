package com.github.txtrpg.message;

/**
 * @author gushakov
 */
public enum Color {

    cyan("\u001B[1;36m"),
    yellow("\u001B[1;33m");

    private String prefix;


    Color(String prefix) {
        this.prefix = prefix;
    }

    public String escape(String text){
        return prefix + text + "\u001B[0m";
    }


}
