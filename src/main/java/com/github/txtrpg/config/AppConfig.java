package com.github.txtrpg.config;

import com.github.txtrpg.actions.ActionProcessor;
import com.github.txtrpg.json.GameUnmarshaller;
import com.github.txtrpg.npc.NpcController;
import com.github.txtrpg.server.GameServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;

/**
 * @author gushakov
 */
@Configuration
public class AppConfig {

    // thread pool factory bean for creating new threads for processing new clients
    @Bean
    public ThreadPoolExecutorFactoryBean pooledExecutorsFactory() {
        ThreadPoolExecutorFactoryBean factory = new ThreadPoolExecutorFactoryBean();
        factory.setCorePoolSize(10);
        factory.setThreadNamePrefix("player-");
        return factory;
    }

    // lifecycle is managed by the pooledExecutorsFactory bean factory
    public ExecutorService commandsTaskExecutor() {
        try {
            return pooledExecutorsFactory().getObject();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Bean
    public ThreadPoolTaskExecutor actionsTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        return taskExecutor;
    }

    @Bean
    public ThreadPoolTaskScheduler daemonScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        return scheduler;
    }

    @Bean
    public ActionProcessor actionProcessor() {
        ActionProcessor processor = new ActionProcessor();
        processor.setActionsTaskExecutor(actionsTaskExecutor());
        return processor;
    }

    @Bean
    public NpcController npcController() {
        NpcController controller = new NpcController();
        controller.setActionProcessor(actionProcessor());
        return controller;
    }

    @Bean
    public GameUnmarshaller worldUnmarshaller() {
        GameUnmarshaller unmarshaller = new GameUnmarshaller();
        unmarshaller.setScenesFileResource(new ClassPathResource("scenes.json"));
        unmarshaller.setNpcFileResource(new ClassPathResource("npcs.json"));
        unmarshaller.setNpcController(npcController());
        return unmarshaller;
    }

    @Bean
    public GameServer gameServer() {
        GameServer server = new GameServer();
        server.setCommandsTaskExecutor(commandsTaskExecutor());
        server.setActionProcessor(actionProcessor());
        server.setDaemonScheduler(daemonScheduler());
        server.setGameUnmarshaller(worldUnmarshaller());
        server.setNpcController(npcController());
        return server;
    }


}
