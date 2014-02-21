package com.github.txtrpg.utils;

/**
 * @author gushakov
 */
public class ConsoleUtils {

    public static String color(String text) {
        return text
                .replaceAll("-([^-]+?)-", "\u001B[1;31m$1\u001B[0m")                //red (negative)
                .replaceAll("\\+([^\\+]+?)\\+", "\u001B[1;32m$1\u001B[0m")          //green (positive)
                .replaceAll("_([^_]+?)_", "\u001B[1;33m$1\u001B[0m")                //yellow (on the ground)
                .replaceAll("\\*([^\\*]+?)\\*", "\u001B[1;34m$1\u001B[0m")          //blue (star in the sky)
                .replaceAll("@([^@]+?)@", "\u001B[1;35m$1\u001B[0m")                //magenta (magic)
                .replaceAll("#([^#]+?)#", "\u001B[1;36m$1\u001B[0m");               //messages (admin)
    }

}
