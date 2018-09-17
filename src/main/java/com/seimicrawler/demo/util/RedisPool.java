package com.seimicrawler.demo.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
/**
 * Author     : WindAsMe
 * File       : RedisPool.java
 * Time       : Create on 18-9-17
 * Location   : ../Home/SeimiCrawler/RedisPool.java
 */
public final class RedisPool {

    private static String ADDRESS =  "127.0.0.1";
    private static Integer PORT = 6379;
    private static Integer MAX_TOTAL = 1024;
    private static Integer MAX_IDLE = 200;
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    private static Boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /*
      Static: init
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDRESS, PORT, TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // READ ONLY
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
