package com.github.txtrpg.message;

/**
 * @author gushakov
 */
public enum ColumnLayout {

    list(2, "%s\t%s")
    ;

    private int numOfColumns;
    private String template;

    ColumnLayout(int numOfColumns, String template) {
        this.numOfColumns = numOfColumns;
        this.template = template;
    }

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public String getTemplate() {
        return template;
    }
}
