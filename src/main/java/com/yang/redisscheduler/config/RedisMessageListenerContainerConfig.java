package com.yang.redisscheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Yang
 * @date: 2018/9/17 22:54
 * @description:
 */
@Configuration
public class RedisMessageListenerContainerConfig {

    private String topic = "__keyevent@0__:expired";

    @Autowired
    private TopicMessageListener messageListener;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 配置监听器容器
     *
     * @param executor
     * @return
     */
    @Bean
    public RedisMessageListenerContainer configRedisMessageListenerContainer(Executor executor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置Redis的连接工厂
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        // 设置监听使用的线程池
        container.setTaskExecutor(executor);
        // 设置监听的Topic
        ChannelTopic channelTopic = new ChannelTopic(this.topic);
        // 设置监听器
        container.addMessageListener(messageListener, channelTopic);
        return container;
    }

    /**
     * 配置监听器线程池
     *
     * @return
     */
    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("myThreadPool");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
