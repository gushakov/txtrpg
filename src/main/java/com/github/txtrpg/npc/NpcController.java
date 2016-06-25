package com.github.txtrpg.npc;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.MoveAction;
import com.github.txtrpg.actions.NoOpAction;
import com.github.txtrpg.actions.SpawnAction;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Npc;
import com.github.txtrpg.core.Spawn;
import com.github.txtrpg.core.World;

import java.util.*;

/**
 * @author gushakov
 */
public class NpcController {

    private World world;

    private Map<String, NpcType> npcDictionary;

    public void setWorld(World world) {
        this.world = world;
    }

    public void setNpcDictionary(Map<String, NpcType> npcDictionary) {
        this.npcDictionary = npcDictionary;
    }

    public NpcType getNpcType(String name) {
        return npcDictionary.get(name);
    }

    public List<Action> start(World world) {
        this.world = world;

        List<Action> actions = new ArrayList<>();

        // get all spawn locations
        npcDictionary.values().stream().forEach(type -> {
            final Spawn spawn = type.getSpawn();
            final String location = spawn.getLocations().stream().findFirst().get();
            // make npc
            final Npc npc = new Npc(type.getName(), type.getDescription(), world.getScene(location));
            actions.add(new SpawnAction(npc, this));
        });

        return actions;
    }

    public List<Action> nextActions(Npc npc) {

        if (new Random(System.currentTimeMillis()).nextInt(10) >= 8) {
            ArrayList<Exit> exits = new ArrayList<>(npc.getLocation().getExits());
            Collections.shuffle(exits);
            return Collections.singletonList(new MoveAction(npc, exits.stream().findAny().get().getDir()));
        }

        return Collections.singletonList(new NoOpAction(npc));

    }

}
