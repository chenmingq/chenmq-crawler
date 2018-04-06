package com.chenmq.crawler.cache.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.cache.pool
 * @Description: TODO
 * @date 2018-04-05 下午7:08
 */


@Configuration
@EnableCaching
public class JedisRedisConfig {

    private JedisPool jedisPool = null;

    @Value("${spring.redis.host}")
    private  String host;
    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.timeout}")
    private  int timeout;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;


    /**
     * @param config
     * @return
     */
    @Autowired
    @Bean(name = "jedis.pool")
    public JedisPool redisPoolFactory(@Qualifier("jedis.pool.config") JedisPoolConfig config) {
        this.jedisPool = new JedisPool(config, this.host, this.port, this.timeout,this.password);
        return this.jedisPool;
    }


    /**
     * 配置redis连接池配置
     * @return
     */
    @Bean(name= "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig () {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(this.maxIdle);
        config.setMinIdle(this.minIdle);
        return config;
    }

}
