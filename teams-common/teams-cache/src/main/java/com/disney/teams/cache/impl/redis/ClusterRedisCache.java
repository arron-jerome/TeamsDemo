/*
 * Copyright 2016-2020 1HAITAO.COM. All rights reserved.
 */
package com.disney.teams.cache.impl.redis;

import com.yhtframework.cache.CacheRuntimeException;
import com.disney.teams.cache.impl.AbstractCache;
import com.yhtframework.cache.impl.redis.utils.RedisCacheUtils;
import com.yhtframework.cache.impl.redis.utils.RedisRunnable;
import com.yhtframework.model.vo.StatusCode;
import com.yhtframework.utils.type.ArrayUtils;
import com.yhtframework.utils.type.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * //TODO
 *
 * @author lucky.liu
 * @version 1.0.0
 * @email liuwb2010@gmail.com
 * @date 2016-03-09
 * Modification  History:
 * Date         Author        Version        Description
 * ------------------------------------------------------
 * 2016-03-09   lucky.liu     1.0.0          create
 */
public class ClusterRedisCache extends AbstractCache {

    public static final Long SUCC_CODE = 1L;
    public static final String SUCC_CODE_STR = "OK";

    private JedisCluster jedisCluster;

    ClusterRedisCache() {
    }

    void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    @Override
    public boolean exists(String key) {
        return jedisCluster.exists(buildKey(key));
    }

    @Override
    public <T> T get(String key, int timeoutSeconds) {
        String s = jedisCluster.get(buildKey(key));
        T value = valueSerializer.unSerialize(s);
        return value;
    }

    @Override
    public <T> T get(String key, int timeoutSeconds, Class<T> clz) {
        String s = jedisCluster.get(buildKey(key));
        T value = valueSerializer.unSerialize(s, clz);
        return value;
    }

    @Override
    public <T> boolean add(String key, T value, int expiredSeconds) {
        if (isValidExpiredTime(expiredSeconds)) {
            String rs = jedisCluster.set(buildKey(key), valueSerializer.serialize(value), "NX", "EX", expiredSeconds);
            return SUCC_CODE_STR.equals(rs);
        } else if (expiredSeconds == 0) {
            return !jedisCluster.exists(buildKey(key));
        } else {
            Long rs = jedisCluster.setnx(buildKey(key), valueSerializer.serialize(value));
            return SUCC_CODE.equals(rs);
        }
    }

    @Override
    public <T> boolean put(final String key, final T value, final int expiredSeconds) {
        if (isValidExpiredTime(expiredSeconds)) {
            return RedisCacheUtils.execute(key, new RedisRunnable<Boolean>() {
                @Override
                public Boolean execute(Jedis jedis) {
                    final String fullKey = buildKey(key);
                    final int retryCount = RedisCacheUtils.TRANSACTION_MAX_RETRY_COUNT + 1;
                    //发现数据已经被修改，重试最多TRANSACTION_MAX_RETRY_COUNT次，直到成功为止

                    String valueStr = valueSerializer.serialize(value);
                    for (int i = 1; i < retryCount; ++i) {
                        jedis.watch(fullKey);
                        try {
                            //事务开始
                            Transaction tx = jedis.multi();
                            Response<String> setRes = tx.set(fullKey, valueStr);

                            Response<Long> expireRes = tx.expire(fullKey, expiredSeconds);

                            //执行队列中的任务并提交
                            //TODO 如果失败，仍可能有数据被提交，待优化
                            List<Object> list = tx.exec();
                            if (CollectionUtils.isEmpty(list)) {
                                continue;
                            }

                            if (SUCC_CODE_STR.equals(setRes.get()) && SUCC_CODE.equals(expireRes.get())) {
                                return Boolean.TRUE;
                            } else {
                                jedis.unwatch();
                                return Boolean.FALSE;
                            }
                        } catch (RuntimeException e) {
                            jedis.unwatch();
                            throw e;
                        }
                    }
                    throw new CacheRuntimeException(StatusCode.SERVER_ERROR_CODE, String.format("Execute key '%s' transaction, rollback %s count!", fullKey, retryCount));
                }
            });
        } else if (expiredSeconds == 0) {
            return !jedisCluster.exists(buildKey(key));
        } else {
            String rs = jedisCluster.set(buildKey(key), valueSerializer.serialize(value));
            return SUCC_CODE_STR.equals(rs);
        }
    }

    @Override
    public boolean delete(String key) {
        Long ok = jedisCluster.del(buildKey(key));
        return SUCC_CODE.equals(ok);
    }

    @Override
    public int delete(String... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            return 0;
        }
        String[] nkeys = new String[keys.length];
        for (int i = 0, len = keys.length; i < len; ++i) {
            nkeys[i] = buildKey(keys[i]);
        }
        Long count = jedisCluster.del(nkeys);
        return count == null ? 0 : count.intValue();
    }

    @Override
    public long incr(String key, long delta, long initValue, long timeoutSeconds, int expiredSeconds) {
        if (initValue > 0) {
            long value = delta + initValue;
            boolean added = add(key, value, expiredSeconds);
            if (added) {
                return value;
            }
        }
        long result = jedisCluster.incrBy(buildKey(key), delta);
        //仅第一次请求需要设置超时时间
        if (result == delta + initValue) {
            expire(key, expiredSeconds);
        }
        return result;
    }

    @Override
    public void expire(String key, int expiredSeconds) {
        if (isValidExpiredTime(expiredSeconds)) {
            String buildKey = buildKey(key);
            for (int i = 0; i < 3; ++i) {
                Long result = jedisCluster.expire(buildKey, expiredSeconds);
                if (SUCC_CODE.equals(result)) {
                    return;
                } else {
                    logger.warn("Set expire by key {} return error code {}", buildKey, result);
                }
            }
            logger.error("Set expire by key {} error retry count 3!", buildKey);
        }
    }

    @Override
    public int ttl(String key) {
        Long ttl = jedisCluster.ttl(buildKey(key));
        if (ttl == null || ttl == -2) {
            return CACHE_NOT_EXISTS;
        } else if (ttl == -1) {
            return CACHE_NO_EXPIRE;
        }
        return ttl.intValue();
    }
}
