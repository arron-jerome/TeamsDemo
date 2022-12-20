package com.disney.teams.cache.impl.redis.utils;

import redis.clients.jedis.Jedis;
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
public interface RedisRunnable<T> {

    T execute(Jedis connection);

}
