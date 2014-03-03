package com.github.txtrpg.server;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.World;
import com.github.txtrpg.json.WorldUnmarshaller;
import com.github.txtrpg.tasks.DaemonTask;
import com.github.txtrpg.tasks.PlayerInputTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author gushakov
 */
public class GameServer {
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);

    private World world;

    private ThreadPoolTaskExecutor commandsTaskExecutor;

    private ActionProcessor actionProcessor;

    private ThreadPoolTaskScheduler daemonScheduler;

    private WorldUnmarshaller worldUnmarshaller;

    public void setCommandsTaskExecutor(ThreadPoolTaskExecutor commandsTaskExecutor) {
        this.commandsTaskExecutor = commandsTaskExecutor;
    }

    public void setActionProcessor(ActionProcessor actionProcessor) {
        this.actionProcessor = actionProcessor;
    }

    public void setDaemonScheduler(ThreadPoolTaskScheduler daemonScheduler) {
        this.daemonScheduler = daemonScheduler;
    }

    public void setWorldUnmarshaller(WorldUnmarshaller worldUnmarshaller) {
        this.worldUnmarshaller = worldUnmarshaller;
    }

    @PostConstruct
    public void init() {
        world = worldUnmarshaller.unmarshal();
        daemonScheduler.scheduleAtFixedRate(new DaemonTask(world, actionProcessor), 500);
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
                    player.setLocation(world.getScenes().get("s1"));
                    player.sendMessage("Welcome to the *Game*");
                    String rawInput;
                    while ((rawInput = socketReader.readLine()) != null) {
                        commandsTaskExecutor.submit(new PlayerInputTask(world, rawInput, actionProcessor));
                        player.updateStatus();
                    }

                }
            }
        }


    }

}
