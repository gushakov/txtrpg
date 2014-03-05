package com.github.txtrpg.json;

import com.github.txtrpg.core.Dir;
import com.github.txtrpg.core.Item;
import com.github.txtrpg.core.Scene;
import com.github.txtrpg.core.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertThat;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WorldUnmarshallerTest.TestConfig.class})
public class WorldUnmarshallerTest {

    @Configuration
    public static class TestConfig {

        @Autowired
        private Environment env;

        @Bean
        public WorldUnmarshaller worldUnmarshaller() {
            WorldUnmarshaller loader = new WorldUnmarshaller();
            loader.setScenesFileResource(new ClassPathResource("scenes.json"));
            return loader;
        }

    }

    @Autowired
    private WorldUnmarshaller worldUnmarshaller;

    @Test
    public void testUnmarshal() throws Exception {
        World world = worldUnmarshaller.unmarshal();
        assertThat(world.getScenes().values(), iterableWithSize(3));
        Scene s1 = world.getScenes().get("s1");
        Scene s2 = world.getScenes().get("s2");
        Scene s3 = world.getScenes().get("s3");
        assertThat(s1.getExit(Dir.n).get().getTo(), is(s2));
        assertThat(s2.getExit(Dir.s).get().getTo(), is(s1));
        assertThat(s2.getExit(Dir.n).get().getTo(), is(s3));
        assertThat(s3.getExit(Dir.s).get().getTo(), is(s2));
        assertThat(s1.getGround().isFull(), is(false));
        assertThat(s1.getGround().getWeight(), is(0));
        Optional<Item> c1 = s1.getGround().take("c1");
        assertThat(c1.isPresent(), is(true));
        assertThat(c1.get(), hasProperty("description", is("silver coin")));
        assertThat(s1.getGround().isEmpty(), is(true));
    }
}
