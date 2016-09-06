package com.yimei.finance.config;

import com.yimei.cloudservice.queue.api.Queue;
import com.yimei.cloudservice.queue.api.QueueProvider;
import com.yimei.cloudservice.queue.redis.RedisQueueProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangqi on 16/9/6.
 */
@Slf4j
@Configuration
public class QueueConfig {

    @Value("${notification.mailQueue}")
    private String queueName;

    @Value("${redisqueue.hostname}")
    private String redisHost;

    @Value("${redisqueue.port}")
    private int redisPort;

    @Bean
    QueueProvider queueProvider() {
        return new RedisQueueProvider(redisHost, redisPort);
    }

    @Bean
    public Queue getQueue(QueueProvider queueProvider){
        return queueProvider.getQueue(queueName);
    }

}
