package com.github.txtrpg.repository;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Scene;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author gushakov
 */
public interface SceneRepository extends GraphRepository<Scene> {

    Scene findById(Long id);

    Scene findByName(String name);

    @Query("START from=node({0}) MATCH (from)-[e:EXIT_TO]->(to) WHERE e.dir = {1} RETURN to")
    Scene findByExitTo(Scene scene, Dir dir);
}
