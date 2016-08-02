package com.yimei.boot.config;

import com.yimei.boot.ext.redis.AegisRedisSerializer;
import org.redisson.Config;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.ExpiringSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by hary on 16/3/15.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600*24*7)//site模块session默认两小时
public class ExtRedisConfig {
    private
    @Value("${redis.hostname}")
    String redisHostName;

    private
    @Value("${redis.port}")
    int redisPort;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory fact = new JedisConnectionFactory();
        fact.setHostName(redisHostName);
        fact.setPort(redisPort);
        fact.setUsePool(true);
        return fact;
    }

    /**
     * 个性化持久类，session中保存的bean改变时不报错
     *
     * @param connectionFactory
     * @return
     */

    @Bean
    public RedisTemplate<String, ExpiringSession> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ExpiringSession> template = new RedisTemplate<String, ExpiringSession>();
        template.setHashValueSerializer(new AegisRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public Redisson getRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress(redisHostName + ":" + redisPort).setConnectionPoolSize(5);
        return Redisson.create(config);
    }
}
