package com.disney.teams.cache.impl.redis;

import com.disney.teams.cache.CacheRuntimeException;
import com.disney.teams.cache.impl.AbstractCache;
import com.disney.teams.cache.impl.redis.utils.RedisCacheUtils;
import com.disney.teams.cache.impl.redis.utils.RedisRunnable;
import com.disney.teams.utils.type.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

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
public class RedisCache extends AbstractCache {

    public static final Long SUC_CODE = 1L;
    public static final String SUC_CODE_STR = "OK";

    private JedisPool jedisPool;

    RedisCache() {
    }

    public RedisCache(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    @Override
    public boolean exists(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.exists(buildKey(key));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, int timeoutSeconds) {
        try (Jedis ignored = jedisPool.getResource()) {
            String s = ignored.get(buildKey(key));
            return valueSerializer.unSerialize(s);
        }
    }

    @Override
    public <T> T get(String key, int timeoutSeconds, Class<T> clz) {
        try (Jedis ignored = jedisPool.getResource()) {
            String s = ignored.get(buildKey(key));
            return valueSerializer.unSerialize(s, clz);
        }
    }

    @Override
    public <T> boolean add(String key, T value, int expiredSeconds) {
        try (Jedis ignored = jedisPool.getResource()) {
            if (isValidExpiredTime(expiredSeconds)) {
                SetParams setParams = SetParams.setParams().ex(expiredSeconds)
                        .nx();
                String rs = ignored.set(buildKey(key)
                        , valueSerializer.serialize(value), setParams);
                return SUC_CODE_STR.equals(rs);
            } else if (expiredSeconds == 0) {
                return !ignored.exists(buildKey(key));
            } else {
                Long rs = ignored.setnx(buildKey(key), valueSerializer.serialize(value));
                return SUC_CODE.equals(rs);
            }
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

                            if (SUC_CODE_STR.equals(setRes.get()) && SUC_CODE.equals(expireRes.get())) {
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
                    throw new CacheRuntimeException(String.format("Execute key '%s' transaction, rollback %s count!", fullKey, retryCount));
                }
            });
        } else if (expiredSeconds == 0) {
            return !jedis.exists(buildKey(key));
        } else {
            String rs = jedis.set(buildKey(key), valueSerializer.serialize(value));
            return SUC_CODE_STR.equals(rs);
        }
    }

    @Override
    public boolean delete(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            Long ok = ignored.del(buildKey(key));
            return SUC_CODE.equals(ok);
        }
    }

    @Override
    public long incr(final String key, final long value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.incrBy(buildKey(key), value);
        }
    }

    @Override
    public long incr(String key, long value, int expiredSeconds) {
        String targetKey = buildKey(key);
        try (Jedis ignored = jedisPool.getResource()) {
            long result = ignored.incrBy(targetKey, value);
            if (isValidExpiredTime(expiredSeconds)) {
                ignored.expire(targetKey, expiredSeconds);
            }
            return result;
        }
    }

    @Override
    public void expire(String key, int expiredSeconds) {
        if (!isValidExpiredTime(expiredSeconds)) {
            return;
        }
        try (Jedis ignored = jedisPool.getResource()) {
            String targetKey = buildKey(key);
            for (int i = 0; i < 3; ++i) {
                Long result = ignored.expire(targetKey, expiredSeconds);
                if (SUC_CODE.equals(result)) {
                    return;
                } else {
                    logger.warn("Set expire by key {} return error code {}", targetKey, result);
                }
            }
            logger.error("Set expire by key {} error retry count 3!", targetKey);
        }
    }

    @Override
    public int ttl(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            long ttl = ignored.ttl(buildKey(key));
            if (ttl == -2) {
                return CACHE_NOT_EXISTS;
            } else if (ttl == -1) {
                return CACHE_NO_EXPIRE;
            }
            return (int) ttl;
        }
    }
}
