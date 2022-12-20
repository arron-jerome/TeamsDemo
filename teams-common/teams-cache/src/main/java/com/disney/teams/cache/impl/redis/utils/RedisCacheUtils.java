package com.disney.teams.cache.impl.redis.utils;

import com.alibaba.fastjson.JSON;
import com.disney.teams.cache.CacheRuntimeException;
import com.disney.teams.cache.ICache;
import com.disney.teams.cache.impl.AbstractCache;
import com.disney.teams.cache.impl.redis.cluster.ClusterRedisCache;
import com.disney.teams.cache.impl.redis.RedisCache;
import com.disney.teams.cache.serializer.ValueSerializer;
import com.disney.teams.log.timer.AutoTimeLog;
import com.disney.teams.utils.type.CollectionUtils;
import com.disney.teams.utils.type.FieldUtils;
import com.disney.teams.utils.type.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClusterCommand;
import redis.clients.jedis.JedisClusterConnectionHandler;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
public class RedisCacheUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheUtils.class);

    public static int TRANSACTION_MAX_RETRY_COUNT = 100;

    public static final Long SUCC_CODE = 1L;
    public static final String SUCC_CODE_STR = "OK";

    //100毫秒超时打印超时日志
    private static AutoTimeLog autoTimeLog = new AutoTimeLog(100);

    private static ICache cache;
    private static JedisCommands jedis;
    private static JedisClusterConnectionHandler connectionHandler;
    private static int maxRedirections;
    private static ValueSerializer<String> serializer;

    public void setCache(ICache cache) {
        cache(cache);
    }

    public static ICache cache() {
        return cache;
    }

    public static void cache(ICache cache) {
        RedisCacheUtils.cache = cache;
        RedisCacheUtils.serializer = ((AbstractCache) cache).getValueSerializer();
        if (cache instanceof ClusterRedisCache) {
            RedisCacheUtils.jedis = ((ClusterRedisCache) cache).getJedisCluster();
            RedisCacheUtils.connectionHandler = FieldUtils.getValue(RedisCacheUtils.jedis, "connectionHandler");
            Integer maxAttempts = FieldUtils.getValue(RedisCacheUtils.jedis, "maxAttempts");
            //兼容老版本字段
            if (maxAttempts == null) {
                maxAttempts = FieldUtils.getValue(RedisCacheUtils.jedis, "maxRedirections");
            }
            RedisCacheUtils.maxRedirections = (maxAttempts == null ? 5 : maxAttempts);
        } else if (cache instanceof RedisCache) {
            jedis = ((RedisCache) cache).getJedis();
        }
    }

    public static <T> T hget(String key, String field) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            String value = jedis.hget(cache.getFullKey(key), field);
            return serializer.unSerialize(value);
        }
    }

    public static <T> T hget(String key, String field, Class<T> clazz) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            String value = jedis.hget(cache.getFullKey(key), field);
            return serializer.unSerialize(value, clazz);
        }
    }

    public static Map<String, Object> hGetAll(String key) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            Map<String, String> map = jedis.hgetAll(cache.getFullKey(key));
            if (MapUtils.isEmpty(map)) {
                return new HashMap<>();
            }
            Map<String, Object> realMap = new HashMap<>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                realMap.put(entry.getKey(), serializer.unSerialize(entry.getValue()));
            }
            return realMap;
        }
    }

    public static <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            Map<String, String> map = jedis.hgetAll(cache.getFullKey(key));
            if (MapUtils.isEmpty(map)) {
                return new HashMap<>();
            }
            Map<String, T> realMap = new HashMap<>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                realMap.put(entry.getKey(), serializer.unSerialize(entry.getValue(), clazz));
            }
            return realMap;
        }
    }

    private static final Long HSET_ADD = 1l;
    private static final Long HSET_UPDATE = 0l;

    public static void hset(String key, String field, Object value) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
//            Long ok = jedis.hset(cache.getFullKey(key), field, serializer.serialize(value));
            Long rs = jedis.hset(cache.getFullKey(key), field, serializer.serialize(value));
            if (!HSET_ADD.equals(rs) && !HSET_UPDATE.equals(rs)) {
                String message = String.format("Hset failed code %s by key %s value %s", rs, key, value);
                log.error(message);
                throw new BasicRuntimeException(StatusCode.SERVER_ERROR_CODE, message);
            }
            // 返回值：
            // 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
            // 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
//            if(!SUCC_CODE.equals(ok)) {
//                log.error("Hset failed code {} by key {} value {}", ok, key, value);
//                throw new BasicRuntimeException(StatusCode.SERVER_ERROR);
//            }
        }
    }

    public static boolean hsetnx(String key, String field, Object value) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            Long ok = jedis.hsetnx(cache.getFullKey(key), field, serializer.serialize(value));
            return SUCC_CODE.equals(ok);
        }
    }

    public static void hset(String key, Map<String, Object> valueMap) {
        if (MapUtils.isEmpty(valueMap)) {
            return;
        }
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            Map<String, String> strValueMap = new HashMap<>(valueMap.size());
            for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                strValueMap.put(entry.getKey(), serializer.serialize(entry.getValue()));
            }
            String rs = jedis.hmset(cache.getFullKey(key), strValueMap);
            if (!SUCC_CODE_STR.equals(rs)) {
                log.error("Hset failed {} by key {} map {}", rs, key, JSON.toJSONString(valueMap));
                throw new BasicRuntimeException(StatusCode.SERVER_ERROR);
            }
        }
    }

    public static <BEAN> void hset(String key, BEAN bean) {
        Map<String, Object> map = MethodUtils.invokeAllGetterMethod(bean);
        hset(key, map);
    }

    public static void hdelAll(String key) {
        delete(key);
    }

    public static void hdel(String key, String... fields) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            Long size = Long.valueOf(fields.length);
            Long rs = jedis.hdel(cache.getFullKey(key), fields);
            if (!size.equals(rs)) {
                log.warn("Delete failed number {} by key {} fields {}", rs, key, JSON.toJSONString(fields));
            }
        }
    }

    public static long hincre(String key, String field, long value) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            long now = jedis.hincrBy(cache.getFullKey(key), field, value);
            return now;
        }
    }

    /**
     * 高并发场景慎用此方法
     *
     * @param key
     * @param increMap
     * @return
     */
    public static Map<String, Long> hincre(final String key, final Map<String, Long> increMap) {
        if (MapUtils.isEmpty(increMap)) {
            return increMap;
        }
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            return execute(key, jedis -> {
                final String fullKey = cache.getFullKey(key);
                final int retryCount = TRANSACTION_MAX_RETRY_COUNT + 1;
                //发现数据已经被修改，重试最多TRANSACTION_MAX_RETRY_COUNT次，直到成功为止
                for (int i = 1; i < retryCount; ++i) {
                    jedis.watch(fullKey);
                    try {
                        //事务开始
                        Transaction tx = jedis.multi();

                        Map<String, Response<Long>> map = new HashMap<>(increMap.size());
                        for (Map.Entry<String, Long> entry : increMap.entrySet()) {
                            //加入执行队列
                            Response<Long> response = tx.hincrBy(fullKey, entry.getKey(), entry.getValue());
                            map.put(entry.getKey(), response);
                        }

                        //执行队列中的任务并提交
                        //TODO 如果失败，仍可能有数据被提交，待优化
                        List<Object> list = tx.exec();
                        if (CollectionUtils.isEmpty(list)) {
//                                log.warn("Execute key '{}' transaction, rollback {} count!", fullKey, i);
                            continue;
                        }
                        //结果汇总返回
                        Map<String, Long> resultMap = new HashMap<>(map.size());
                        for (Map.Entry<String, Response<Long>> entry : map.entrySet()) {
                            resultMap.put(entry.getKey(), entry.getValue().get());
                        }
                        return resultMap;
                    } catch (RuntimeException e) {
                        jedis.unwatch();
                        throw e;
                    }
                }
                throw new CacheRuntimeException(String.format("Execute key '%s' transaction, rollback %s count!", fullKey, retryCount));
            });
        }
    }

    public static <T> T execute(String key, final RedisRunnable<T> run) {
        try (AutoTimeLog ignore = autoTimeLog.warn()) {
            if (cache instanceof ClusterRedisCache) {
                return new JedisClusterCommand<T>(connectionHandler, maxRedirections) {
                    @Override
                    public T execute(Jedis jedis) {
                        return run.execute(jedis);
                    }
                }.run(cache.getFullKey(key));
            } else {
                RedisCache c = (RedisCache) cache;
                RedisClient client = c.getJedis();
                try (Jedis ignored = client.getJedisPool().getResource()) {
                    return run.execute(ignored);
                }
            }
        }
    }

    /**
     * 当缓存的ttl小于给定的ttl时，更新缓存数据
     *
     * @param key            缓存key值
     * @param timeoutSeconds 放入缓存的超时时间
     * @param ttl            缓存剩余时间下限，小于此值是缓存数据需要被更新
     * @param supplier       获取数据方法
     * @param <T>            数据类型
     * @return
     */
    public static <T> T putIfTtl(String key, int timeoutSeconds, int ttl, Supplier<T> supplier) {
        int remainSeconds = cache.ttl(key);
        //-1表示key存在未设置超时时间，-2表示key值不存在
        boolean needPut = (remainSeconds == -2 || remainSeconds < ttl);
        if (!needPut) {
            return cache.get(key);
        }

        T value = supplier.get();
        if (value == null) {
            return value;
        }

        cache.put(key, value, timeoutSeconds);
        return value;
    }

    // delegate methods for cache -------------------------------------------

    public static void setDefalutExpiredTime(int expiredTime) {
        cache.setDefalutExpiredTime(expiredTime);
    }

    public static <T> T get(String key, int timeout, Class<T> clz) {
        return cache.get(key, timeout, clz);
    }

    public static long incr(String key, long delta, long initValue, long timeout, int exp) {
        return cache.incr(key, delta, initValue, timeout, exp);
    }

    public static long incr(String key, long value) {
        return cache.incr(key, value);
    }

    public static String getFullKey(String key) {
        return cache.getFullKey(key);
    }

    public static <T> boolean add(String key, T value) {
        return cache.add(key, value);
    }

    public static long decr(String key, long value) {
        return cache.decr(key, value);
    }

    public static <T> boolean add(String key, T value, int expiredTime) {
        return cache.add(key, value, expiredTime);
    }

    public static <T> boolean put(String key, T value) {
        return cache.put(key, value);
    }

    public static void setDefalutTimeOut(int timeout) {
        cache.setDefalutTimeOut(timeout);
    }

    public static boolean delete(String key) {
        return cache.delete(key);
    }

    public static int getDefalutTimeOut() {
        return cache.getDefalutTimeOut();
    }

    public static int getDefalutExpiredTime() {
        return cache.getDefalutExpiredTime();
    }

    public static <T> T get(String key, Class<T> clz) {
        return cache.get(key, clz);
    }

    public static long incr(String key, long value, int exp) {
        return cache.incr(key, value, exp);
    }

    public static String getPrefixKey() {
        return cache.getPrefixKey();
    }

    public static void setPrefixKey(String prefixKey) {
        cache.setPrefixKey(prefixKey);
    }

    public static boolean exists(String key) {
        return cache.exists(key);
    }

    public static <T> T get(String key) {
        return cache.get(key);
    }

    public static <T> T get(String key, int timeout) {
        return cache.get(key, timeout);
    }

    public static long decr(String key, long delta, long initValue, long timeout, int exp) {
        return cache.decr(key, delta, initValue, timeout, exp);
    }

    public static <T> boolean put(String key, T value, int expiredTime) {
        return cache.put(key, value, expiredTime);
    }

    public static void expire(String key, int exp) {
        cache.expire(key, exp);
    }
}
