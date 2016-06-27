package com.github.txtrpg.npc;

import com.github.txtrpg.actions.*;
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
        System.out.println("-----");
        System.out.println("-----");
        System.out.println("-----");
        System.out.println("-----");
        System.out.println("-----");

        /*
        if (new Random(System.currentTimeMillis()).nextInt(10) >= 8) {
            System.out.println("*");
            return Collections.singletonList(new MoveAction(npc, npc.getLocation().getRandomExitDirection()));
        }

        System.out.println(".");
        return Collections.singletonList(new NoOpAction(npc));
*/
        return Collections.singletonList(new DelayedAction(new MoveAction(npc, npc.getLocation().getRandomExitDirection()), 20));
    }

}
