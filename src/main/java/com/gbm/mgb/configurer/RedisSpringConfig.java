package com.gbm.mgb.configurer;

import com.gbm.mgb.utils.redis.JedisClientPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:redis.properties")
public class RedisSpringConfig {

    @Value("${redis.maxTotal}")
    private Integer redisMaxTotal;

    @Value("${redis.node1.host}")
    private String redisNode1Host;

    @Value("${redis.node1.port}")
    private Integer redisNode1Port;
    @Value("${redis.node1.password}")
    private String passWord;
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        return jedisPoolConfig;
    }
    @Bean
    public JedisClientPool jedisClientPool(){
        return new JedisClientPool();
    }
    @Bean
    public ShardedJedisPool shardedJedisPool() {
        List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(redisNode1Host, redisNode1Port);
        jedisShardInfo.setPassword(passWord);
        jedisShardInfos.add(jedisShardInfo);
        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfos);
    }
}