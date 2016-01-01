package com.haozileung.infra.cache.redis;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.haozileung.infra.cache.Cache;
import com.haozileung.infra.cache.CacheException;
import com.haozileung.infra.cache.CacheProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Redis 缓存实现
 *
 */
public class RedisCacheProvider implements CacheProvider {

    private static String host;
    private static int port;
    private static int timeout;
    private static String password;
    private static int database;

    private static JedisPool pool;

    public static Jedis getResource() {
        return pool.getResource();
    }

    private static String getProperty(Properties props, String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }

    private static int getProperty(Properties props, String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @Override
    public Cache buildCache(String regionName) throws CacheException {
        return new RedisCache(regionName);
    }

    @Override
    public void start() throws CacheException {
        String conf = "redis.properties";
        final URL url = Resources.getResource(conf);
        final ByteSource byteSource = Resources.asByteSource(url);
        final Properties props = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = byteSource.openBufferedStream();
            props.load(inputStream);
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        JedisPoolConfig config = new JedisPoolConfig();

        host = getProperty(props, "host", "127.0.0.1");
        password = props.getProperty("password", null);

        port = getProperty(props, "port", 6379);
        timeout = getProperty(props, "timeout", 2000);
        database = getProperty(props, "database", 0);

        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        // config.setBlockWhenExhausted(true);
        // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        // config.setEvictionPolicyClassName(
        // "org.apache.commons.pool2.impl.DefaultEvictionPolicy" );
        // 是否启用pool的jmx管理功能, 默认true
        // config.setJmxEnabled( true );
        // MBean ObjectName = new
        // ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" +
        // "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
        // config.setJmxNamePrefix( "pool" );
        // 是否启用后进先出, 默认true
        // config.setLifo( true );
        // 最大空闲连接数, 默认8个
        config.setMaxIdle(50);
        // 最小空闲连接数, 默认0
        config.setMinIdle(5);
        // 最大连接数, 默认8个
        config.setMaxTotal(500);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        config.setMaxWaitMillis(3000);

        // 在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(true);
        // 返回一个jedis实例给连接池时，是否检查连接可用性（ping()）
        config.setTestOnReturn(true);
        // 在空闲时检查有效性, 默认false
        config.setTestWhileIdle(true);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1000L * 60L * 1L);
        // 对象空闲多久后逐出, 当空闲时间>该值 ，且 空闲连接>最大空闲数
        // 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)，默认30m
        config.setSoftMinEvictableIdleTimeMillis(1000L * 60L * 1L);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(60000); // 1m
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(10);
        pool = new JedisPool(config, host, port, timeout, password, database);

    }

    @Override
    public void stop() {
        pool.destroy();
    }
}