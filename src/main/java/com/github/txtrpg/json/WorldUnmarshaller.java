package com.github.txtrpg.json;

import com.github.txtrpg.core.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class WorldUnmarshaller {

    private static final Logger logger = LoggerFactory.getLogger(WorldUnmarshaller.class);

    private Resource scenesFileResource;

    private Resource npcFileResource;

    public void setScenesFileResource(Resource scenesFileResource) {
        this.scenesFileResource = scenesFileResource;
    }

    public void setNpcFileResource(Resource npcFileResource) {
        this.npcFileResource = npcFileResource;
    }

    public World unmarshal() {

        Assert.notNull(scenesFileResource);
        Assert.notNull(npcFileResource);

        World world = new World();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // load scenes from json
            ArrayList<Scene> jsonScenes = mapper.readValue(scenesFileResource.getInputStream(),
                    new TypeReference<List<Scene>>() {
                    }
            );

            Map<String, Scene> scenesMap = jsonScenes.stream()
                    .collect(Collectors.toMap(Scene::getName, Function.identity()));

            for (Scene scene : scenesMap.values()) {
                // create a ground container, if needed
                if (scene.getGround() == null) {
                    scene.setGround(new Container<>("ground", "ground"));
                }

                // for each exit in every scene update a "from" scene and the "to" to point to the
                // actual scenes from the map
                for (Exit exit : scene.getExits()) {
                    exit.setFrom(scene);
                    exit.setTo(scenesMap.get(exit.getTo().getName()));
                }
                scenesMap.put(scene.getName(), scene);
            }

            world.setScenes(scenesMap);

            // load npcs types from josn

            ArrayList<NpcType> jsonNpcs = mapper.readValue(npcFileResource.getInputStream(),
                    new TypeReference<List<NpcType>>() {
                    });

            Map<String, NpcType> npcDictionary = jsonNpcs.stream()
                    .collect(Collectors.toMap(NpcType::getName, Function.identity()));

            world.setNpcDictionary(npcDictionary);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return world;
    }

}
