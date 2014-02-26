package com.github.txtrpg.server;

import com.github.txtrpg.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author gushakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class GameServerTest {

    @Autowired
    private GameServer server;

    @Test
    public void testStartServer() throws Exception {
       assertThat(server, notNullValue());
       server.start();
    }
}
