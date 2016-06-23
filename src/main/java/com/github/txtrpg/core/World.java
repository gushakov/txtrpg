package com.github.txtrpg.core;

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

    private Map<String, NpcType> npcDictionary;

    private Map<String, Player> players;

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public Map<String, NpcType> getNpcDictionary() {
        return npcDictionary;
    }

    public void setNpcDictionary(Map<String, NpcType> npcDictionary) {
        this.npcDictionary = npcDictionary;
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

}
