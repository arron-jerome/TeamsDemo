/*
 * Copyright 2016-2020 1HAITAO.COM. All rights reserved.
 */
package com.disney.teams.cache.impl.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisShardInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * //TODO
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version 1.0.0
 * @date 2016-03-08
 * Modification  History:
 * Date         Author        Version        Description
 * ------------------------------------------------------
 * 2016-03-08   lucky.liu     1.0.0          create
 */
public class JedisShardInfoFactory {

    /**
     * 构建jedis shards集群
     * @param hosts
     * @return
     */
    public static List<JedisShardInfo> build(String hosts) {
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>();
        String[] hostArrays = hosts.split(",");
        for(String hostStr : hostArrays) {
            String[] ipAndPort = hostStr.split(":");
            String ip = ipAndPort[0];
            int port = Integer.valueOf(ipAndPort[1]);

            JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, port);
            jedisShardInfoList.add(jedisShardInfo);
        }
        return jedisShardInfoList;
    }

    /**
     * 构建集群HostAndPort配置
     * @param hosts
     * @return
     */
    public static Set<HostAndPort> buildHostAndPorts(String hosts) {
        Set<HostAndPort> jedisShardInfoList = new HashSet<HostAndPort>();
        String[] hostArrays = hosts.split(",");
        for(String hostStr : hostArrays) {
            String[] ipAndPort = hostStr.split(":");
            String ip = ipAndPort[0];
            int port = Integer.valueOf(ipAndPort[1]);

            HostAndPort jedisShardInfo = new HostAndPort(ip, port);
            jedisShardInfoList.add(jedisShardInfo);
        }
        return jedisShardInfoList;
    }
}
