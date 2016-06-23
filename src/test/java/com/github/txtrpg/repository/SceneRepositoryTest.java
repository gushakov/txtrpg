package com.github.txtrpg.repository;

import com.github.txtrpg.json.WorldUnmarshaller;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
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

import static org.hamcrest.Matchers.equalTo;
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
        public WorldUnmarshaller worldLoader() {
            WorldUnmarshaller loader = new WorldUnmarshaller();
            loader.setScenesFileResource(new ClassPathResource("scenes.json"));
            loader.setNpcFileResource(new ClassPathResource("npcs.json"));
            return loader;
        }

    }

    @Autowired
    private WorldUnmarshaller loader;

    @Autowired
    private GraphDatabaseService graphDb;

    @Autowired
    private SceneRepository repository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    public void testSave() throws Exception {
        assertThat(repository.count(), equalTo(0L));
    }

    @After
    public void tearDown() throws Exception {
        graphDb.shutdown();
    }
}
