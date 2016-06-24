package com.github.txtrpg.core;

import com.github.txtrpg.npc.NpcType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gushakov
 */
public class World {

    public World() {
        players = new HashMap<>();
    }

    private Map<String, Scene> scenes;

    private Map<String, Player> players;

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public void setScenes(Map<String, Scene> scenes) {
        this.scenes = scenes;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public Scene getScene(String location){
        return scenes.get(location);
    }
}
