package com.disney.teams.cache.impl.redis;

import com.disney.teams.cache.ICache;
import com.disney.teams.cache.impl.redis.utils.RedisCacheUtils;
import com.disney.teams.cache.serializer.FastJsonSerializer;
import com.disney.teams.utils.type.StringUtils;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author arron.zhou
 * @version 1.0.0
 * @date 2022/12/20
 * Description:
 * Modification  History:
 * Date         Author        Version        Description
 * ------------------------------------------------------
 * 2022/12/20       arron.zhou      1.0.0          create
 */
public class RedisClusterCacheFactory extends AbstractRedisCacheFactory {

    private int connectionTimeout;
    private int soTimeout;
    private int maxRedirections;
    private String password;

    private ClusterRedisCache cache;

    @Override
    public ICache getObject() throws Exception {
        if (cache != null) {
            return cache;
        }
        synchronized (this) {
            if (cache != null) {
                return cache;
            }
            Set<HostAndPort> hostAndPort = JedisShardInfoFactory.buildHostAndPorts(servers);
            ConnectionPoolConfig config = buildConnectConfig();
            JedisCluster jedisCluster;
            if (StringUtils.isBlank(password)) {
                jedisCluster = new JedisCluster(hostAndPort, connectionTimeout,
                        soTimeout, maxRedirections, config);
            } else {
                jedisCluster = new JedisCluster(hostAndPort, connectionTimeout,
                        soTimeout, maxRedirections, password, config);
            }
            cache = new ClusterRedisCache();
            cache.setValueSerializer(new FastJsonSerializer());
            cache.setPrefixKey(serverId);
            cache.setJedisCluster(jedisCluster);
            if (defaultRedis) {
                RedisCacheUtils.cache(cache);
            }
            return cache;
        }
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
