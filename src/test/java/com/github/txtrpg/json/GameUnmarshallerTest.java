package com.github.txtrpg.json;

import com.github.txtrpg.core.*;
import com.github.txtrpg.npc.NpcController;
import com.github.txtrpg.npc.NpcType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertThat;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GameUnmarshallerTest.TestConfig.class})
public class GameUnmarshallerTest {

    @Configuration
    public static class TestConfig {

        @Bean
        public NpcController npcController() {
            return new NpcController();
        }

        @Bean
        public GameUnmarshaller worldUnmarshaller() {
            GameUnmarshaller loader = new GameUnmarshaller();
            loader.setScenesFileResource(new ClassPathResource("scenes.json"));
            loader.setNpcFileResource(new ClassPathResource("npcs.json"));
            loader.setNpcController(npcController());
            return loader;
        }

    }

    @Autowired
    private GameUnmarshaller gameUnmarshaller;

    @Autowired
    private NpcController npcController;

    @Test
    public void testContainer() throws Exception {
        Container<Item> chest = new Container<>("chest1", "big wooden chest");
        assertThat(chest.isEmpty(), is(true));
        chest.put(new Item("a little bottle", "a little bottle", 1));
        chest.put(new Item("copper coin", "copper coin"));
        chest.put(new Item("silver coin", "silver coin"));
        chest.put(new Item("small gold coin", "small gold coin"));
        assertThat(chest.getWeight(), is(Integer.MAX_VALUE));
        Container<Item> bag = new Container<>("bag1", "leather bag", 1);
        assertThat(bag.getWeight(), is(1));
        chest.put(bag);
        assertThat(chest.find("bottle"), iterableWithSize(1));
        assertThat(chest.find("coin"), iterableWithSize(3));
    }

    @Test
    public void testUnmarshal() throws Exception {
        World world = gameUnmarshaller.unmarshal();
        assertThat(world.getScenes().values(), iterableWithSize(3));
        Scene s1 = world.getScenes().get("s1");
        Scene s2 = world.getScenes().get("s2");
        Scene s3 = world.getScenes().get("s3");
        assertThat(s1.getExit(Dir.n).get().getTo(), is(s2));
        assertThat(s2.getExit(Dir.s).get().getTo(), is(s1));
        assertThat(s2.getExit(Dir.n).get().getTo(), is(s3));
        assertThat(s3.getExit(Dir.s).get().getTo(), is(s2));

        assertThat(s1.getGround().find("co"), iterableWithSize(3));
        assertThat(s1.getGround().find("silver"), iterableWithSize(1));
        assertThat(s1.getGround().find("copper"), iterableWithSize(1));
        assertThat(s1.getGround().find("gold"), iterableWithSize(1));

        Optional<Item> coin1 = s1.getGround().take("silver coin");
        assertThat(coin1.isPresent(), is(true));
        assertThat(coin1.get(), hasProperty("name", is("silver coin")));
    }

    @Test
    public void testUnmarshallNpcs() throws Exception {
        gameUnmarshaller.unmarshal();
        NpcType butterfly = npcController.getNpcType("butterfly");
        assertThat(butterfly, notNullValue());
        Spawn spawn = butterfly.getSpawn();
        assertThat(spawn, notNullValue());
        assertThat(spawn.getLocations(), Matchers.contains("s3"));
    }
}
