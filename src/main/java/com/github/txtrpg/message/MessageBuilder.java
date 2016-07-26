package com.github.txtrpg.message;

/**
 * @author gushakov
 */
public class MessageBuilder {

    enum Color {
        yellow("\u001B[1;33m");

        private String prefix;


        Color(String prefix) {
            this.prefix = prefix;
        }

        public String escape(String text){
          return prefix + text + "\u001B[0m";
        }
    }

    private StringBuilder builder;

    public static Color yellow(){
        return Color.yellow;
    }

    public MessageBuilder() {
        builder = new StringBuilder();
    }

    public MessageBuilder(String text) {
        builder = new StringBuilder(text);
    }

    public MessageBuilder br() {
        builder.append("\n\r");
        return this;
    }

    public MessageBuilder append(Color color, String text){
        builder.append(color.escape(text));
        return this;
    }

    public String build(){
        return builder.toString();
    }

}
