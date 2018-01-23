package com.gbm.mgb.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisClientPool {
	
	@Autowired
	private ShardedJedisPool jedisPool;

 
	public String set(String key, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

 
	public String get(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

 
	public Boolean exists(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

 
	public Long expire(String key, int seconds) {
		ShardedJedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

 
	public Long ttl(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

 
	public Long incr(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

 
	public Long hset(String key, String field, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

 
	public String hget(String key, String field) {
		ShardedJedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

 
	public Long hdel(String key, String... field) {
		ShardedJedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(key, field);
		jedis.close();
		return hdel;
	}	

}
