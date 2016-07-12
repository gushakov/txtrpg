package com.github.txtrpg.json;

import com.github.txtrpg.core.*;
import com.github.txtrpg.logic.LogicController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertNotNull;
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
        public LogicController npcController() {
            return new LogicController();
        }

        @Bean
        public GameUnmarshaller worldUnmarshaller() {
            GameUnmarshaller loader = new GameUnmarshaller();
            loader.setScenesFileResource(new ClassPathResource("scenes.json"));
            loader.setNpcFileResource(new ClassPathResource("npcs.json"));
            return loader;
        }

    }

    @Autowired
    private GameUnmarshaller gameUnmarshaller;

    @Test
    public void testContainer() throws Exception {
        Container<Item> bag = new Container<>("bag1", "leather bag", false, 10);
        assertThat(bag.getWeight(), is(0));
        bag.put(new Item("a little bottle", "a little bottle", 2));
        bag.put(new Item("copper coin", "copper coin", 1));
        bag.put(new Item("silver coin", "silver coin", 1));
        bag.put(new Item("small gold coin", "small gold coin", 1));
        assertThat(bag.getWeight(), is(5));
    }

    @Test
    public void testUnmarshal() throws Exception {
        gameUnmarshaller.unmarshal();
        World world = gameUnmarshaller.getWorld();
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
        final Map<String, NpcType> npcDictionary = gameUnmarshaller.getNpcDictionary();
        final NpcType butterfly = npcDictionary.get("butterfly");
        SpawnType spawnType = butterfly.getSpawn();
        assertThat(spawnType, notNullValue());
        assertThat(spawnType.getLocations(), Matchers.contains("s1", "s2", "s3"));
        final CorpseType corpseType = butterfly.getCorpse();
        assertNotNull(corpseType);
        List<ItemType> itemTypes = corpseType.getItems();
        assertThat(itemTypes, Matchers.iterableWithSize(1));
        ItemType wing = itemTypes.get(0);
        assertThat(wing, hasProperty("name", equalTo("wing of a butterfly")));
    }
}
