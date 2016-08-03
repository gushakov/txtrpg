package com.github.txtrpg.message;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author gushakov
 */
public enum Color {

    yellow("\u001B[1;33m", "_"),

    red("\u001B[1;31m", "-"),

    green("\u001B[1;32m", "+"),

    blue("\u001B[1;34m", "*"),

    cyan("\u001B[1;36m", "#"),

    white("\u001B[1;37m", "~");

    private String prefix;

    private String delimiter;

    public static Color forDelimiter(String delimiter) {
        if (delimiter.equals(yellow.delimiter)) {
            return yellow;
        } else if (delimiter.equals(red.delimiter)) {
            return red;
        } else if (delimiter.equals(green.delimiter)) {
            return green;
        } else if (delimiter.equals(blue.delimiter)) {
            return blue;
        } else if (delimiter.equals(cyan.delimiter)) {
            return cyan;
        } else if (delimiter.equals(white.delimiter)) {
            return white;
        } else {
            throw new IllegalArgumentException("Delimiter " + delimiter + " not found");
        }
    }

    public static String getAllDelimiters() {
        return StringUtils.join(Arrays.stream(Color.values()).map(Color::getDelimiter).toArray());
    }

    public static boolean isDelimiter(String delimiter) {
        return getAllDelimiters().contains(delimiter);
    }

    Color(String prefix, String delimiter) {
        this.prefix = prefix;
        this.delimiter = delimiter;
    }

    public String escape(String text) {
        return prefix + text + "\u001B[0m";
    }

    public String getDelimiter() {
        return delimiter;
    }
}
