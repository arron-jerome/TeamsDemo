package com.disney.teams.cache.impl.redis;

import com.disney.teams.cache.impl.AbstractCache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

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

    public static final Long SUCC_CODE = 1L;
    public static final String SUCC_CODE_STR = "OK";

    private RedisClient jedis;

    RedisCache() {}

    void setJedis(RedisClient jedis) {
        this.jedis = jedis;
    }

    public RedisClient getJedis() {
        return jedis;
    }

    @Override
    public boolean exists(String key) {
        return jedis.exists(buildKey(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, int timeoutSeconds) {
        String s = jedis.get(buildKey(key));
        T value = valueSerializer.unSerialize(s);
        return value;
    }

    @Override
    public <T> T get(String key, int timeoutSeconds, Class<T> clz) {
        String s = jedis.get(buildKey(key));
        T value = valueSerializer.unSerialize(s, clz);
        return value;
    }

    @Override
    public <T> boolean add(String key, T value, int expiredSeconds) {
        if(isValidExpiredTime(expiredSeconds)) {
            String rs = jedis.set(buildKey(key), valueSerializer.serialize(value), "NX", "EX", expiredSeconds);
            return SUCC_CODE_STR.equals(rs);
        } else if(expiredSeconds == 0){
            return !jedis.exists(buildKey(key));
        } else {
            Long rs = jedis.setnx(buildKey(key), valueSerializer.serialize(value));
            return SUCC_CODE.equals(rs);
        }
    }

    @Override
    public <T> boolean put(final String key, final T value, final int expiredSeconds) {
        if(isValidExpiredTime(expiredSeconds)) {
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

                            if(SUCC_CODE_STR.equals(setRes.get()) && SUCC_CODE.equals(expireRes.get())) {
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
        } else if(expiredSeconds == 0) {
            return !jedis.exists(buildKey(key));
        } else {
            String rs = jedis.set(buildKey(key), valueSerializer.serialize(value));
            return SUCC_CODE_STR.equals(rs);
        }
    }

    @Override
    public boolean delete(String key) {
        Long ok = jedis.del(buildKey(key));
        return SUCC_CODE.equals(ok);
    }

    @Override
    public long incr(final String key, final long value) {
        long result = jedis.incrBy(buildKey(key), value);
        return result;
    }

    @Override
    public long incr(String key, long delta, long initValue, long timeoutSeconds, int expiredSeconds) {
        String targetKey = buildKey(key);
        long result = jedis.incrBy(targetKey, delta);
        if(isValidExpiredTime(expiredSeconds)) {
            jedis.expire(targetKey, expiredSeconds);
        }
        return result;
    }

    @Override
    public void expire(String key, int expiredSeconds) {
        if(isValidExpiredTime(expiredSeconds)) {
            String targetKey = buildKey(key);
            for(int i = 0; i < 3; ++i) {
                Long result = jedis.expire(targetKey, expiredSeconds);
                if(SUCC_CODE.equals(result)) {
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
        Long ttl = jedis.ttl(buildKey(key));
        if(ttl == null || ttl == -2) {
            return CACHE_NOT_EXISTS;
        } else if(ttl == -1) {
            return CACHE_NO_EXPRIE;
        }
        return ttl.intValue();
    }
}
