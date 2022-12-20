package com.disney.teams.cache.impl.redis;

import com.disney.teams.cache.ICache;
import com.disney.teams.cache.serializer.FastJsonSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.List;

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
public class RedisCacheFactory extends AbstractRedisCacheFactory {

    private RedisCache cache;

    private String password;

    private List<JedisShardInfo> build(String hosts) {
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>();
        String[] hostArrays = hosts.split(",");
        for (String hostStr : hostArrays) {
            String[] ipAndPort = hostStr.split(":");
            String ip = ipAndPort[0];
            int port = Integer.parseInt(ipAndPort[1]);
            JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, port);
            jedisShardInfoList.add(jedisShardInfo);
        }
        return jedisShardInfoList;
    }

    @Override
    public ICache getObject() throws Exception {
        if (cache != null) {
            return cache;
        }

        synchronized (this) {
            if (cache != null) {
                return cache;
            }
            List<JedisShardInfo> infoList = JedisShardInfoFactory.build(servers);
            JedisShardInfo info = infoList.get(0);
            info.setPassword(password);
            JedisPoolConfig config = buildPoolConfig();
            JedisPool jedisPool = new JedisPool(config, info.getHost(), info.getPort(), Protocol.DEFAULT_TIMEOUT, info.getPassword());
            cache = new RedisCache();
            cache.setValueSerializer(new FastJsonSerializer());
            cache.setPrefixKey(serverId);
            cache.setJedis(new RedisClient(jedisPool));
            if (defaultRedis) {
                RedisCacheUtils.cache(cache);
            }
            return cache;
        }
    }
}
