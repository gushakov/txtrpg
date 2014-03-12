package com.github.txtrpg.tasks;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author gushakov
 */
public class ServerTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ServerTask.class);

    private World world;
    private ActionProcessor actionProcessor;
    private ExecutorService commandsTaskExecutor;

    public ServerTask(World world, ActionProcessor actionProcessor, ExecutorService commandsTaskExecutor) {
        this.world = world;
        this.actionProcessor = actionProcessor;
        this.commandsTaskExecutor = commandsTaskExecutor;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(3333);
            logger.debug("Started server");
            while (true) {
                final Socket socket = server.accept();
                logger.debug("Accepted connection from {}", socket.getRemoteSocketAddress());
                commandsTaskExecutor.execute(new PlayerInputTask(world, actionProcessor, socket));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                    commandsTaskExecutor.shutdown();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
