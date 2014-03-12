package com.github.txtrpg.server;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.World;
import com.github.txtrpg.json.WorldUnmarshaller;
import com.github.txtrpg.tasks.DaemonTask;
import com.github.txtrpg.tasks.ServerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author gushakov
 */
public class GameServer {
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);

    private World world;

    private ExecutorService commandsTaskExecutor;

    private ActionProcessor actionProcessor;

    private ThreadPoolTaskScheduler daemonScheduler;

    private WorldUnmarshaller worldUnmarshaller;

    public void setCommandsTaskExecutor(ExecutorService commandsTaskExecutor) {
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
        daemonScheduler.scheduleAtFixedRate(new DaemonTask(actionProcessor), 500);
    }

    public void start() throws IOException, InterruptedException {
        new ServerTask(world, actionProcessor, commandsTaskExecutor).run();
    }

}
