package com.chenmq.crawler.cache.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author chenmq
 * @version V1.0
 * @ProjectName: chenmq-crawler
 * @Package com.chenmq.crawler.cache.pool
 * @Description: TODO
 * @date 2018-04-05 下午9:26
 */
@Service
public class RedisSourcesCache {

    private Logger logger = LoggerFactory.getLogger(RedisSourcesCache.class);


    @Resource
    private JedisPool jedisPool;


    /**
     * redis set
     * @param k
     * @param v
     * @return
     */
    public String set (String k , String v){
        String result = jedisPool.getResource().set(k,v);
        logger.info("SET {} {}",k,v);
        return result;
    }

    /**
     * hash 方式使用hset添加数据
     * @param key
     * @param field
     * @param value
     */
    public void hset (String key , String field , String value) {
        logger.info("HSET "+ key + " " + field +" " + value + new Date());
        jedisPool.getResource().hset(key,field,value);
    }


    /**
     * redis 单个hget获取数据
     * @param key
     * @param field
     * @return
     */
    public String hget (String key , String field) {
        logger.info("HGET "+ key + " " + field + new Date());
        return jedisPool.getResource().hget(key,field);
    }

    



}
