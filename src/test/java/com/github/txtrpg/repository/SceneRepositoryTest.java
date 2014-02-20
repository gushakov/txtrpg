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
@ContextConfiguration
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

        Transaction tx = null;
        try  {
            tx = graphDb.beginTx();
            Scene s1 = new Scene("s1");
            Scene s2 = new Scene("s2");
            repository.save(Arrays.asList(s1, s2));
            s1.addExit(Dir.n, s2);
            repository.save(Arrays.asList(s1, s2));
            tx.success();
        }
        catch (Exception e){
e.printStackTrace();
            if (tx != null) {
                tx.failure();
            }
        }
        finally {
           if (tx != null) {
               tx.finish();
           }
        }

        assertThat(repository.count(), greaterThan(0L));
        assertThat(repository.findById(1L), is(repository.findByName("s1")));
        assertThat(repository.findById(2L), is(repository.findByName("s2")));
        assertThat(repository.findByExitTo(repository.findById(1L), Dir.n),
                is(repository.findById(2L)));


    }

    @After
    public void tearDown() throws Exception {
        graphDb.shutdown();

    }
}
