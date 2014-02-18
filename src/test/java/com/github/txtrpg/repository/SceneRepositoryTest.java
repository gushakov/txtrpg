package com.github.txtrpg.repository;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Exit;
import com.github.txtrpg.core.Scene;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.After;
import org.junit.Assert;
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
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@Transactional
public class SceneRepositoryTest {

    @Configuration
    @EnableNeo4jRepositories(basePackages = {"com.github.txtrpg.repository"})
    public static class TestConfig extends Neo4jConfiguration {

        @Bean
        public GraphDatabaseService graphDatabaseService(){
            return new TestGraphDatabaseFactory().newImpermanentDatabase();
        }

    }

    @Autowired
    private GraphDatabaseService graphDb;

    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;


    @Test
    public void testSetup() throws Exception {
        assertThat(graphDb, notNullValue());
        assertThat(sceneRepository, notNullValue());
        assertThat(neo4jTemplate, notNullValue());
        assertThat(sceneRepository.count(), equalTo(0L));
    }

    @Test
    public void testSave() throws Exception {

        Transaction tx = graphDb.beginTx();
        try {
            Scene s1 = sceneRepository.save(new Scene("Dark Forest"));
            Scene s2 = sceneRepository.save(new Scene("Dark Forest Path"));
            Exit e1 = new Exit(Dir.n, s1, s2);
            s1.addExit(e1);
            Exit e2 = new Exit(Dir.s, s2, s1);
            s2.addExit(e2);
            sceneRepository.save(s1);
            sceneRepository.save(s2);

            tx.success();
        }
        finally {
            tx.finish();
        }

       assertThat(sceneRepository.count(), equalTo(2L));
       assertThat(sceneRepository.findByExitsDir(Dir.n), IsIterableWithSize.<Exit>iterableWithSize(1));

    }

    @After
    public void tearDown() throws Exception {
        graphDb.shutdown();

    }
}
