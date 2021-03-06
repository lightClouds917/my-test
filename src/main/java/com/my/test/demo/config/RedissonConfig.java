package com.my.test.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient getRedissonClient() {
        try {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + host + ":" + port)
                    .setPassword(password)
                    //设置对于master节点的连接池中连接数最大为500
                    .setConnectionPoolSize(500)
                    //如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒
                    .setIdleConnectionTimeout(10000)
                    //同任何节点建立连接时的等待超时。时间单位是毫秒。
                    .setConnectTimeout(30000)
                    //等待节点回复命令的时间。该时间从命令发送成功时开始计时。
                    .setTimeout(3000)
                    //当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
                    .setReconnectionTimeout(3000)
                    //在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。默认值：1500
                    .setRetryInterval(3000)
                    //如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。默认值：3
                    .setRetryAttempts(3);
            log.info("初始化Redisson 结束");
            return Redisson.create(config);
        } catch (Exception e) {
            log.error("Redisson init error",e);
        }
        return null;
    }
}
