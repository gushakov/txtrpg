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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class GameUnmarshaller {

    private static final Logger logger = LoggerFactory.getLogger(GameUnmarshaller.class);

    private Resource scenesFileResource;

    private Resource npcFileResource;

    private World world;

    private Map<String, NpcType> npcDictionary;

    public void setScenesFileResource(Resource scenesFileResource) {
        this.scenesFileResource = scenesFileResource;
    }

    public void setNpcFileResource(Resource npcFileResource) {
        this.npcFileResource = npcFileResource;
    }

    public World getWorld() {
        return world;
    }

    public Map<String, NpcType> getNpcDictionary() {
        return npcDictionary;
    }

    public void unmarshal() {

        Assert.notNull(scenesFileResource);
        Assert.notNull(npcFileResource);
        world = new World();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // load scenes from json
            final ArrayList<SceneType> jsonScenes = mapper.readValue(scenesFileResource.getInputStream(),
                    new TypeReference<List<SceneType>>() {
                    }
            );

            final Map<String, Scene> scenesMap = new HashMap<>();

            jsonScenes.stream().forEach(sceneType -> {
                final Scene scene = new Scene(sceneType.getName(), sceneType.getDescription());

                final ItemType groundType = sceneType.getGround();
                if (groundType != null) {
                    final Ground ground = new Ground();
                    groundType.getItems().forEach(itemType -> {
                        if (itemType.getItems() != null) {
                            final Container<Item> container = new Container<>(itemType.getName(), itemType.getDescription());
                            itemType.getItems().forEach(itType -> {
                                container.put(new Item(itType.getName(), itType.getDescription(), itType.getWeight()));
                            });
                            ground.put(container);
                        } else {
                            ground.put(new Item(itemType.getName(), itemType.getDescription(), itemType.getWeight()));
                        }
                    });
                    scene.setGround(ground);
                }

                scenesMap.put(scene.getName(), scene);
            });

            jsonScenes.stream().forEach(sceneType -> {
                final Scene scene = scenesMap.get(sceneType.getName());
                sceneType.getExits().forEach(exitType -> scene.addExit(Dir.valueOf(exitType.getDir()), scenesMap.get(exitType.getTo())));
            });

            world.setScenes(scenesMap);

            // load NPC types from json

            final ArrayList<NpcType> jsonNpcs = mapper.readValue(npcFileResource.getInputStream(),
                    new TypeReference<List<NpcType>>() {
                    });

            npcDictionary = jsonNpcs.stream()
                    .collect(Collectors.toMap(NpcType::getName, Function.identity()));


        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

}
