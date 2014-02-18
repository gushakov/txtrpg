package com.github.txtrpg.repository;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author gushakov
 */
public interface SceneRepository extends GraphRepository<Scene> {
    Scene findByName(String name);

    Iterable<Exit> findByExitsDir(Dir dir);
}
