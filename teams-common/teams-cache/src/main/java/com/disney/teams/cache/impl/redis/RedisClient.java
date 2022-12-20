package com.disney.teams.cache.impl.redis;


import redis.clients.jedis.JedisPool;
import redis.clients.jedis.commands.JedisCommands;

import java.util.List;
import java.util.Map;
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
public class RedisClient implements JedisCommands {

    private JedisPool jedisPool;

    RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    @Override
    public String set(String key, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.set(key, value);
        }
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.set(key, value, nxxx, expx, time);
        }
    }

    @Override
    public String set(String key, String value, String nxxx) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.set(key, value, nxxx);
        }
    }

    @Override
    public String get(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.get(key);
        }
    }

    @Override
    public Boolean exists(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.exists(key);
        }
    }

    @Override
    public Long persist(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.persist(key);
        }
    }

    @Override
    public String type(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.type(key);
        }
    }

    @Override
    public Long expire(String key, int seconds) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.expire(key, seconds);
        }
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.pexpire(key, milliseconds);
        }
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.expireAt(key, unixTime);
        }
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.pexpireAt(key, millisecondsTimestamp);
        }
    }

    @Override
    public Long ttl(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.ttl(key);
        }
    }

    @Override
    public Long pttl(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.pttl(key);
        }
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.setbit(key, offset, value);
        }
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.setbit(key, offset, value);
        }
    }

    @Override
    public Boolean getbit(String key, long offset) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.getbit(key, offset);
        }
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.setrange(key, offset, value);
        }
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.getrange(key, startOffset, endOffset);
        }
    }

    @Override
    public String getSet(String key, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.getSet(key, value);
        }
    }

    @Override
    public Long setnx(String key, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.setnx(key, value);
        }
    }

    @Override
    public String setex(String key, int seconds, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.setex(key, seconds, value);
        }
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.psetex(key, milliseconds, value);
        }
    }

    @Override
    public Long decrBy(String key, long integer) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.decrBy(key, integer);
        }
    }

    @Override
    public Long decr(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.decr(key);
        }
    }

    @Override
    public Long incrBy(String key, long integer) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.incrBy(key, integer);
        }
    }

    @Override
    public Double incrByFloat(String key, double value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.incrByFloat(key, value);
        }
    }

    @Override
    public Long incr(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.incr(key);
        }
    }

    @Override
    public Long append(String key, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.append(key, value);
        }
    }

    @Override
    public String substr(String key, int start, int end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.substr(key, start, end);
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hset(key, field, value);
        }
    }

    @Override
    public String hget(String key, String field) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hget(key, field);
        }
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hsetnx(key, field, value);
        }
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hmset(key, hash);
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hmget(key, fields);
        }
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hincrBy(key, field, value);
        }
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hincrByFloat(key, field, value);
        }
    }

    @Override
    public Boolean hexists(String key, String field) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hexists(key, field);
        }
    }

    @Override
    public Long hdel(String key, String... field) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hdel(key, field);
        }
    }

    @Override
    public Long hlen(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hlen(key);
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hkeys(key);
        }
    }

    @Override
    public List<String> hvals(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hvals(key);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hgetAll(key);
        }
    }

    @Override
    public Long rpush(String key, String... string) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.rpush(key, string);
        }
    }

    @Override
    public Long lpush(String key, String... string) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lpush(key, string);
        }
    }

    @Override
    public Long llen(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.llen(key);
        }
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lrange(key, start, end);
        }
    }

    @Override
    public String ltrim(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.ltrim(key, start, end);
        }
    }

    @Override
    public String lindex(String key, long index) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lindex(key, index);
        }
    }

    @Override
    public String lset(String key, long index, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lset(key, index, value);
        }
    }

    @Override
    public Long lrem(String key, long count, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lrem(key, count, value);
        }
    }

    @Override
    public String lpop(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lpop(key);
        }
    }

    @Override
    public String rpop(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.rpop(key);
        }
    }

    @Override
    public Long sadd(String key, String... member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sadd(key, member);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.smembers(key);
        }
    }

    @Override
    public Long srem(String key, String... member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.srem(key, member);
        }
    }

    @Override
    public String spop(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.spop(key);
        }
    }

    @Override
    public Set<String> spop(String key, long count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.spop(key, count);
        }
    }

    @Override
    public Long scard(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.scard(key);
        }
    }

    @Override
    public Boolean sismember(String key, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sismember(key, member);
        }
    }

    @Override
    public String srandmember(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.srandmember(key);
        }
    }

    @Override
    public List<String> srandmember(String key, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.srandmember(key, count);
        }
    }

    @Override
    public Long strlen(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.strlen(key);
        }
    }

    @Override
    public Long zadd(String key, double score, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zadd(key, score, member);
        }
    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zadd(key, score, member, params);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zadd(key, scoreMembers);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zadd(key, scoreMembers, params);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrange(key, start, end);
        }
    }

    @Override
    public Long zrem(String key, String... member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrem(key, member);
        }
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zincrby(key, score, member);
        }
    }

    @Override
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zincrby(key, score, member, params);
        }
    }

    @Override
    public Long zrank(String key, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrank(key, member);
        }
    }

    @Override
    public Long zrevrank(String key, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrank(key, member);
        }
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrange(key, start, end);
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeWithScores(key, start, end);
        }
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeWithScores(key, start, end);
        }
    }

    @Override
    public Long zcard(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zcard(key);
        }
    }

    @Override
    public Double zscore(String key, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zscore(key, member);
        }
    }

    @Override
    public List<String> sort(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sort(key);
        }
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sort(key, sortingParameters);
        }
    }

    @Override
    public Long zcount(String key, double min, double max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zcount(key, min, max);
        }
    }

    @Override
    public Long zcount(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zcount(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScore(key, max, min);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScore(key, max, min);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScoreWithScores(key, min, max);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScoreWithScores(key, max, min);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset,
        int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScoreWithScores(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScoreWithScores(key, min, max);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScoreWithScores(key, max, min);
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset,
        int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByScoreWithScores(key, min, max, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
        int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScoreWithScores(key, max, min, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
        int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByScoreWithScores(key, max, min, offset, count);
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zremrangeByRank(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zlexcount(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByLex(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByLex(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrangeByLex(key, max, min);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zrevrangeByLex(key, max, min, offset, count);
        }
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zremrangeByLex(key, min, max);
        }
    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.linsert(key, where, pivot, value);
        }
    }

    @Override
    public Long lpushx(String key, String... string) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.lpushx(key, string);
        }
    }

    @Override
    public Long rpushx(String key, String... string) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.rpushx(key, string);
        }
    }

    @Override
    public List<String> blpop(String arg) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.blpop(arg);
        }
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.blpop(timeout, key);
        }
    }

    @Override
    public List<String> brpop(String arg) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.brpop(arg);
        }
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.brpop(timeout, key);
        }
    }

    @Override
    public Long del(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.del(key);
        }
    }

    @Override
    public String echo(String string) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.echo(string);
        }
    }

    @Override
    public Long move(String key, int dbIndex) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.move(key, dbIndex);
        }
    }

    @Override
    public Long bitcount(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.bitcount(key);
        }
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.bitcount(key, start, end);
        }
    }

    @Override
    public Long bitpos(String key, boolean value) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.bitpos(key, value);
        }
    }

    @Override
    public Long bitpos(String key, boolean value, BitPosParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.bitpos(key, value, params);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hscan(key, cursor);
        }
    }

    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor,
        ScanParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.hscan(key, cursor, params);
        }
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sscan(key, cursor);
        }
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.sscan(key, cursor, params);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.zscan(key, cursor, params);
        }
    }

    @Override
    public Long pfadd(String key, String... elements) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.pfadd(key, elements);
        }
    }

    @Override
    public long pfcount(String key) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.pfcount(key);
        }
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geoadd(key, longitude, latitude, member);
        }
    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geoadd(key, memberCoordinateMap);
        }
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geodist(key, member1, member2);
        }
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geodist(key, member1, member2, unit);
        }
    }

    @Override
    public List<String> geohash(String key, String... members) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geohash(key, members);
        }
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.geopos(key, members);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude,
        double radius, GeoUnit unit) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.georadius(key, longitude, latitude, radius, unit);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.georadius(key, longitude, latitude, radius, unit, param);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius,
        GeoUnit unit) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.georadiusByMember(key, member, radius, unit);
        }
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius,
        GeoUnit unit, GeoRadiusParam param) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.georadiusByMember(key, member, radius, unit, param);
        }
    }

    @Override
    public List<Long> bitfield(String key, String... arguments) {
        try (Jedis ignored = jedisPool.getResource()) {
            return ignored.bitfield(key, arguments);
        }
    }
}
