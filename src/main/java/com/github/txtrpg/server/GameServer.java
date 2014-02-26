package com.github.txtrpg.server;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.World;
import com.github.txtrpg.tasks.DaemonTask;
import com.github.txtrpg.tasks.PlayerInputTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author gushakov
 */

@Component
public class GameServer {
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);

    private World world;

    @Autowired
    private ThreadPoolTaskExecutor commandsTaskExecutor;

    @Autowired
    private ActionProcessor actionProcessor;

    @Autowired
    private ThreadPoolTaskScheduler daemonScheduler;

    @PostConstruct
    public void init(){
        world = new World();
        daemonScheduler.scheduleAtFixedRate(new DaemonTask(world, actionProcessor), 1000);
    }

    public void start() throws IOException {

        while (true) {
            try (ServerSocket server = new ServerSocket(3333)) {
                try (
                        Socket socket = server.accept();
                        PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
                        // Putty telnet sends in in ISO-8859-1 bytes
                        BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"))
                ) {
                    logger.debug("Accepted connection from {}", socket.getRemoteSocketAddress());
                    Player player = new Player(socketWriter);
                    world.setPlayer(player);
                    player.sendMessage("Welcome to the *Game*");
                    player.updateStatus();
                    String rawInput = null;
                    while ((rawInput = socketReader.readLine()) != null) {
                        commandsTaskExecutor.submit(new PlayerInputTask(world, rawInput, actionProcessor));
                        player.updateStatus();
                    }

                }
            }
        }


    }

}
