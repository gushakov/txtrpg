package com.github.txtrpg.loader;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.core.World;
import com.github.txtrpg.repository.SceneRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gushakov
 */
public class WorldLoader {

    private static final Logger logger = LoggerFactory.getLogger(WorldLoader.class);

    private Resource scenesFileResource;

    private SceneRepository repository;

    private GraphDatabaseService graphDb;

    public void setScenesFileResource(Resource scenesFileResource) {
        this.scenesFileResource = scenesFileResource;
    }

    public void setRepository(SceneRepository repository) {
        this.repository = repository;
    }

    public void setGraphDb(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public World unmarshal() {
        World world = new World();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Scene> jsonScenes = mapper.readValue(scenesFileResource.getInputStream(),
                    new TypeReference<List<Scene>>() {
                    });


            Map<String, Scene> scenesMap = new HashMap<>();

            try (Transaction tx = graphDb.beginTx()) {

                for (Scene s : jsonScenes) {
                    Scene scene = repository.save(new Scene(s.getName(), s.getDescription()));
                    scenesMap.put(scene.getName(), scene);
                    logger.debug("{} {}", scene.getId(), scene.getName());
                }


                for (Scene s : jsonScenes) {
                    Scene from = scenesMap.get(s.getName());

                    for (Exit exit : s.getExits()) {
                        Scene to = scenesMap.get(exit.getTo().getName());
                        from.addExit(exit.getDir(), to);
                    }

                    repository.save(from);

                }


                tx.success();
            }

            world.setScenes(scenesMap);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return world;
    }

}
