package com.github.txtrpg.message;

import java.util.Arrays;

/**
 *
 * @author gushakov
 */
public class MessageBuilder {

    private final String EOL = "\n\r";
    private String buffer;

    private boolean tabMode;
    private ColumnLayout columnLayout;
    private String[] columns;
    private int columnIndex;

    public MessageBuilder() {
        this.buffer = "";
    }

    public MessageBuilder append(String text) {
        if (tabMode) {
            columns[columnIndex] += text;
        } else {
            buffer += text;
        }
        return this;
    }

    public MessageBuilder append(int number){
        append(Integer.toString(number));
        return this;
    }

    public MessageBuilder append(int number, Color color){
        append(Integer.toString(number), color);
        return this;
    }

    public MessageBuilder append(String text, Color color) {
        append(color.escape(text));
        return this;
    }

    public MessageBuilder br() {
        append(EOL);
        return this;
    }

    public MessageBuilder withColumns(ColumnLayout layout) {
        this.columnLayout = layout;
        tabMode = true;
        buffer += EOL;
        resetColumns();
        return this;
    }

    public MessageBuilder tab() {
        columnIndex++;
        if (columnIndex == columnLayout.getNumOfColumns()) {
            outputRow();
            resetColumns();
        }
        return this;
    }

    public MessageBuilder end() {
        outputRow();
        tabMode = false;
        return this;
    }

    @Override
    public String toString() {
        return buffer;
    }

    private void resetColumns() {
        columns = new String[columnLayout.getNumOfColumns()];
        Arrays.fill(columns, "");
        columnIndex = 0;
    }

    private void outputRow() {
        buffer += String.format(columnLayout.getTemplate(), columns) + EOL;
    }

}
