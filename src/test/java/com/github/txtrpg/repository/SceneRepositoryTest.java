package com.github.txtrpg.repository;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
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
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

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

        @Bean
        public GraphDatabaseService graphDatabaseService() {
            return new TestGraphDatabaseFactory().newImpermanentDatabase();
        }

    }

    @Autowired
    private GraphDatabaseService graphDb;

    @Autowired
    private SceneRepository repository;


    @Test
    public void testSetup() throws Exception {
        assertThat(graphDb, notNullValue());
        assertThat(repository, notNullValue());
        assertThat(repository.count(), equalTo(0L));
    }

    @Test
    public void testSave() throws Exception {
        try (Transaction tx = graphDb.beginTx()) {
            Scene s0 = new Scene("s0");
            Scene s1 = new Scene("s1");
            repository.save(Arrays.asList(s0, s1));
            s0.addExit(Dir.n, s1);
            repository.save(Arrays.asList(s0, s1));
            tx.success();
        }

        assertThat(repository.count(), greaterThan(0L));
        assertThat(repository.findById(0L), is(repository.findByName("s0")));
        assertThat(repository.findById(1L), is(repository.findByName("s1")));
        assertThat(repository.findByExitTo(repository.findById(0L), Dir.n),
                is(repository.findById(1L)));

    }

    @After
    public void tearDown() throws Exception {
        graphDb.shutdown();
    }
}
