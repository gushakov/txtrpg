package com.github.txtrpg.json;

import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.core.World;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

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

    public void setScenesFileResource(Resource scenesFileResource) {
        this.scenesFileResource = scenesFileResource;
    }

    public World unmarshal() {
        World world = new World();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // load scenes from json
            ArrayList<Scene> jsonScenes = mapper.readValue(scenesFileResource.getInputStream(),
                    new TypeReference<List<Scene>>() {
                    });

            Map<String, Scene> scenesMap = jsonScenes.stream()
                    .collect(Collectors.<Scene, String, Scene>toMap(Scene::getName, Function.identity()));

            // for each exit in every scene update a "from" scene and the "to" to point to the
            // actual scenes from the map
            for (Scene scene : scenesMap.values()) {
                for (Exit exit : scene.getExits()) {
                    exit.setFrom(scene);
                    exit.setTo(scenesMap.get(exit.getTo().getName()));
                }
                scenesMap.put(scene.getName(), scene);
            }

            world.setScenes(scenesMap);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return world;
    }

}
