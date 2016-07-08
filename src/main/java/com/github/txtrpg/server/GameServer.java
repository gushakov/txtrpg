package com.github.txtrpg.server;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.core.World;
import com.github.txtrpg.json.GameUnmarshaller;
import com.github.txtrpg.logic.LogicController;
import com.github.txtrpg.tasks.DaemonTask;
import com.github.txtrpg.tasks.NpcActivateTask;
import com.github.txtrpg.tasks.NpcSpawnTask;
import com.github.txtrpg.tasks.ServerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gushakov
 */
public class GameServer {
    private static final Logger logger = LoggerFactory.getLogger(GameServer.class);

    private World world;

    private ExecutorService commandsTaskExecutor;

    private ActionProcessor actionProcessor;

    private GameUnmarshaller gameUnmarshaller;

    private LogicController logicController;

    public void setCommandsTaskExecutor(ExecutorService commandsTaskExecutor) {
        this.commandsTaskExecutor = commandsTaskExecutor;
    }

    public void setActionProcessor(ActionProcessor actionProcessor) {
        this.actionProcessor = actionProcessor;
    }

    public void setGameUnmarshaller(GameUnmarshaller gameUnmarshaller) {
        this.gameUnmarshaller = gameUnmarshaller;
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
    }

    @PostConstruct
    public void init() {
        gameUnmarshaller.unmarshal();
        world = gameUnmarshaller.getWorld();
        logicController.setWorld(world);
        logicController.setNpcDictionary(gameUnmarshaller.getNpcDictionary());
    }

    public void start() throws IOException, InterruptedException {

        Executors.newSingleThreadExecutor()
                .execute(new NpcActivateTask(actionProcessor, logicController));
        Executors.newSingleThreadExecutor()
                .execute(new NpcSpawnTask(actionProcessor, logicController));
        Executors.newSingleThreadExecutor()
                .execute(new DaemonTask(actionProcessor));

        new ServerTask(world, actionProcessor, commandsTaskExecutor).run();
    }

}
