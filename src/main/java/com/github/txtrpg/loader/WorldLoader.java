package com.github.txtrpg.loader;

import com.github.txtrpg.core.Scene;
import com.github.txtrpg.core.World;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author gushakov
 */
public class WorldLoader {

    private static final Logger logger = LoggerFactory.getLogger(WorldLoader.class);

    private Resource scenesFileResource;

    public void setScenesFileResource(Resource scenesFileResource) {
        this.scenesFileResource = scenesFileResource;
    }


    public World unmarshal(){
        World world = new World();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Scene> scenes = mapper.readValue(new ClassPathResource("scenes.json").getInputStream(), new TypeReference<List<Scene>>() {
            });

            world.setScenes(scenes.stream().collect(Collectors.<Scene, String, Scene>toMap(Scene::getName, Function.<Scene>identity())));

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return world;
    }

}
