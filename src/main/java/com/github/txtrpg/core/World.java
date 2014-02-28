package com.github.txtrpg.core;

import java.util.List;
import java.util.Map;

/**
 * @author gushakov
 */
public class World {

    private Map<String, Scene> scenes;

    private Player player;

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public void setScenes(Map<String, Scene> scenes) {
        this.scenes = scenes;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
