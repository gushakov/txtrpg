package com.github.txtrpg.core;

/**
 * Direction associated with the exit from the scene.
 *
 * @author gushakov
 */
public enum Dir {
    n("north"),
    e("east"),
    s("south"),
    w("west"),
    u("up"),
    d("down");

    private String direction;

    private Dir(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
