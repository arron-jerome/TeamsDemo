package com.disney.teams.cache;

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
public interface ICache {

    int CACHE_NO_EXPIRE = -1;
    int CACHE_NOT_EXISTS = -2;

    /**
     * 设置默认过期时间　
     *
     * @param seconds
     */
    void setDefaultExpiredTime(int seconds);

    /**
     * 获取默认过期时间　
     *
     * @return
     */
    int getDefaultExpiredTime();

    /**
     * 设置默认超时时间　
     *
     * @param timeout
     */
    void setDefaultTimeOut(int timeout);

    /**
     * 获取默认超时时间
     * @return
     */
    int getDefaultTimeOut();

    /**
     * 设置key默认前缀
     * @param prefixKey
     */
    void setPrefixKey(String prefixKey);
    /**
     * 获取key默认前缀
     */
    String getPrefixKey();

    boolean exists(String key);

    String getFullKey(String key);

    /**
     * 从缓存中取值<br />
     * 当存入的数据类型为java.math.BigInteger, BigDecimal, AtomicInteger等Numberic数值类型时，
     * 请使用{@link #get(String, Class)}
     * @param key
     * @return
     */
    <T extends Object> T get(String key);
    <T extends Object> T get(String key, Class<T> clz);

    /**
     * 如果缓存数据的剩余时间小于ttl，则不返回数据
     * @param key
     * @param ttl
     * @param <T>
     * @return
     */
    <T extends Object> T getIfOverTtl(String key, Integer ttl);
    <T extends Object> T getIfOverTtl(String key, Integer ttl, Class<T> clz);

    /**
     * 从缓存中取值<br />
     * 当存入的数据类型为java.math.BigInteger, BigDecimal, AtomicInteger等 Numeric数值类型时，
     * 请使用{@link #get(String, int, Class)}
     * @param key
     * @param timeoutSeconds
     * @return
     */
    <T extends Object> T get(String key, int timeoutSeconds);
    <T extends Object> T get(String key, int timeoutSeconds, Class<T> clz);

    /**
     * 往缓存里加数据，已经存在则返回false
     * @param key
     * @param value
     * @return
     */
    <T extends Object> boolean add(String key, T value);
    <T extends Object> boolean add(String key, T value, int expiredSeconds);

    /**
     * 如果缓存数据的剩余时间小于ttl，则add为true
     * @param key
     * @param ttl
     * @param <T>
     * @return
     */
    <T extends Object> boolean addIfOverTtl(String key, T value, Integer ttl);
    <T extends Object> boolean addIfOverTtl(String key, T value, Integer ttl, int expiredSeconds);

    /**
     * 新增缓存item
     *
     * @param key
     * @param value
     * @return
     */
    <T extends Object> boolean put(String key, T value);

    /**
     * 新增缓存item
     *
     * @param key
     * @param value
     * @param expiredSeconds
     * @return
     */
    <T extends Object> boolean put(String key, T value, int expiredSeconds);

    /**
     * 删除缓存　
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * 删除缓存　
     *
     * @param keys
     * @return
     */
    int delete(String... keys);

    /**
     * 缓存计数加
     * @param key
     * @param value
     * @return
     */
    long incr(String key, long value);

    long incr(String key, long value, int expiredSeconds);

    long incr(String key, long delta, long initValue, long timeoutSeconds, int expiredSeconds);

    /**
     *
     * 缓存计数减
     * @param key
     * @param value
     * @return
     */
    long decr(String key, long value);

    long decr(String key, long delta, long initValue, long timeoutSeconds, int expiredSeconds);

    /**
     * 设置过期时间,适用于redis
     * @param key
     * @param expiredSeconds 单位: seconds
     */
    void expire(String key, int expiredSeconds);

    /**
     * 剩余过期时间
     * @param key
     * @return
     */
    int ttl(String key);

    boolean runIfNotExists(String key, int expiredSeconds, Runnable run);
}

