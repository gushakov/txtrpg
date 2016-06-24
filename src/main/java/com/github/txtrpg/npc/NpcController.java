package com.github.txtrpg.npc;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.actions.SpawnAction;
import com.github.txtrpg.core.Npc;
import com.github.txtrpg.core.Spawn;
import com.github.txtrpg.core.World;

import java.util.Map;

/**
 * @author gushakov
 */
public class NpcController {

    private World world;

    private Map<String, NpcType> npcDictionary;

    private ActionProcessor actionProcessor;

    public void setWorld(World world) {
        this.world = world;
    }

    public void setActionProcessor(ActionProcessor actionProcessor) {
        this.actionProcessor = actionProcessor;
    }

    public void setNpcDictionary(Map<String, NpcType> npcDictionary) {
        this.npcDictionary = npcDictionary;
    }

    public NpcType getNpcType(String name){
        return npcDictionary.get(name);
    }

    public void start(World world){
        this.world = world;
        // get all spawn locations
        npcDictionary.values().stream().forEach(type -> {
            final Spawn spawn = type.getSpawn();
            final String location = spawn.getLocations().stream().findFirst().get();
            final Npc npc = new Npc(type.getName(), type.getDescription(), world.getScene(location));
            actionProcessor.addAction(new SpawnAction(npc));
        });
    }
}
