package com.github.txtrpg.loader;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Scene;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertThat;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WorldLoaderTest.TestConfig.class})
public class WorldLoaderTest {

    @Configuration
    public static class TestConfig {

        @Autowired
        private Environment env;

        @Bean
        public WorldLoader worldLoader() {
            WorldLoader parser = new WorldLoader();
            parser.setScenesFileResource(new ClassPathResource("scenes.json"));
            return parser;
        }

    }

    @Test
    public void testLoadWorld() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Scene> scenes = mapper.readValue(new ClassPathResource("scenes.json").getInputStream(), new TypeReference<List<Scene>>() {
        });
        assertThat(scenes, iterableWithSize(equalTo(3)));
        Scene s1 = scenes.get(0);
        assertThat(s1.getName(), equalTo("s1"));
        assertThat(s1.getDescription(), containsString("forest"));
        assertThat(s1.getExits(), iterableWithSize(1));
        assertThat(s1.getExits(), hasItem(hasProperty("dir", equalTo(Dir.n))));
        assertThat(s1.getExit(Dir.n).getTo(), hasProperty("name", equalTo("s2")));
        Scene s2 = scenes.get(1);
        assertThat(s2.getName(), equalTo("s2"));
        assertThat(s2.getDescription(), containsString("oak"));
        assertThat(s2.getExits(), iterableWithSize(2));
        assertThat(s2.getExits(), hasItem(hasProperty("dir", equalTo(Dir.n))));
        assertThat(s2.getExit(Dir.n).getTo(), hasProperty("name", equalTo("s3")));
        assertThat(s2.getExits(), hasItem(hasProperty("dir", equalTo(Dir.s))));
        assertThat(s2.getExit(Dir.s).getTo(), hasProperty("name", equalTo("s1")));
    }
}
