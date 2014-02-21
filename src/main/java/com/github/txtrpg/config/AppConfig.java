package com.github.txtrpg.config;

import com.github.txtrpg.server.GameServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author gushakov
 */
@Configuration
public class AppConfig {

    @Bean
    public ThreadPoolTaskExecutor playersTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(10);
        return taskExecutor;
    }

    @Bean
    public ThreadPoolTaskScheduler daemonScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        return scheduler;
    }

    @Bean
    public GameServer gameServer() {
        return new GameServer();
    }

}
