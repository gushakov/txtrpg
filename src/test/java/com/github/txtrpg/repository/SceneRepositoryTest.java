package com.github.txtrpg.repository;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.core.World;
import com.github.txtrpg.loader.WorldLoader;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SceneRepositoryTest.TestConfig.class})
public class SceneRepositoryTest {

    @Configuration
    @EnableNeo4jRepositories(basePackages = {"com.github.txtrpg.repository"})
    public static class TestConfig extends Neo4jConfiguration {

        @Autowired
        private SceneRepository repository;

        @Autowired
        private GraphDatabaseService graphDb;

        @Bean
        public GraphDatabaseService graphDatabaseService() {
            return new TestGraphDatabaseFactory().newImpermanentDatabase();
        }

        @Bean
        public WorldLoader worldLoader() {
            WorldLoader loader = new WorldLoader();
            loader.setRepository(repository);
            loader.setGraphDb(graphDb);
            loader.setScenesFileResource(new ClassPathResource("scenes.json"));
            return loader;
        }

    }

    @Autowired
    private WorldLoader loader;

    @Autowired
    private GraphDatabaseService graphDb;

    @Autowired
    private SceneRepository repository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    public void testSetup() throws Exception {
        assertThat(graphDb, notNullValue());
        assertThat(repository, notNullValue());
        assertThat(repository.count(), equalTo(0L));
    }

    @Test
    public void testUnmarshalWorld() throws Exception {
        World world = loader.unmarshal();
        assertThat(repository.count(), equalTo(3L));
        assertThat(repository.findByName("s1").getExit(Dir.n).getTo().getId(), equalTo(1L));
    }

    @After
    public void tearDown() throws Exception {
        graphDb.shutdown();
    }
}
