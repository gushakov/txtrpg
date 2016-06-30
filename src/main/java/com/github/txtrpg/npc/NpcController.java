package com.github.txtrpg.npc;

import com.github.txtrpg.actions.*;
import com.github.txtrpg.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author gushakov
 */
public class NpcController {

    private static final Logger logger = LoggerFactory.getLogger(NpcController.class);

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

    public synchronized List<Action> spawn() {
        List<Action> actions = new ArrayList<>();

        // count all npcs
        final Map<String, Integer> npcsCount = getNpcsCount();

        // get all spawn locations
        npcDictionary.values().stream().forEach(type -> {
            // for each npc type check that number of spawned npcs is less than the maximum number of npc of this type allowed
            if (!npcsCount.containsKey(type.getName()) || npcsCount.get(type.getName()) < type.getSpawn().getNumber()) {
                // can spawn another npc of this type, if successful
                if (new Dice(type.getSpawn().getChance()).success()){
                    final Spawn spawn = type.getSpawn();
                    final List<String> locations = spawn.getLocations();
                    final String location = locations.get(new Dice(locations.size()).index());

                    // make npc
                    final Npc npc = new Npc(type.getName(), type.getDescription(), world.getScene(location));
                    actions.add(new SpawnAction(npc));
                }
            }
        });

        return actions;
    }

    public synchronized List<Action> activate(){
              List<Action> actions = new ArrayList<>();
        getNpcs().parallel().forEach(npc -> {
            final int nextInt = new Random(System.currentTimeMillis()).nextInt(30);
            if (nextInt == 1){
                logger.debug("Activating {}", npc);
                actions.add(new MoveAction(npc, npc.getLocation().getRandomExitDirection()));
            }
        });
        return actions;
    }

    private Stream<Npc> getNpcs(){
       return world.getScenes().values().stream().map(Scene::getRoom).flatMap(Room::getNpcs);
    }

    private Map<String, Integer> getNpcsCount() {
        final Map<String, Integer> map = new HashMap<>();

        getNpcs().forEach(npc -> {
            Integer count = map.get(npc.getName());
            if (count == null) {
                map.put(npc.getName(), 1);
            } else {
                map.put(npc.getName(), count + 1);
            }
        });

        return map;
    }

}
