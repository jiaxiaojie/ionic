package com.thinkgem.jeesite.common.cache.util;

import java.util.Set;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import redis.clients.jedis.Jedis;


public class RedisManager{

	
	public byte[] get(byte[] byteKey) {
		Jedis jedis = null;
		byte[] result = null;
		try{
			jedis = JedisUtils.getResource();
			result = jedis.get(byteKey);
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
		
		return result;
	}

	public void set(byte[] byteKey, byte[] bytes) {
		Jedis jedis = null;
		try{
			jedis = JedisUtils.getResource();
			jedis.set(byteKey, bytes);
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
		
		
	}

	public void del(byte[] byteKey) {
		
		Jedis jedis = null;
		try{
			jedis = JedisUtils.getResource();
			jedis.del(byteKey);
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
	}

	public void flushDB() {
		Jedis jedis = null;
		try{
			jedis = JedisUtils.getResource();
			jedis.flushDB();
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
		
	}

	public String dbSize() {
		Jedis jedis = null;
		String result = null;
		try{
			jedis = JedisUtils.getResource();
			result = jedis.dbSize()+"";
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
		return result;
	}

	public Set<byte[]> keys(String string) {
		
		Jedis jedis = null;
		Set<byte[]> result = null;
		try{
			jedis = JedisUtils.getResource();
			result = jedis.keys(StringUtils.getBytes(string));
		}
		finally{
			JedisUtils.returnResource(jedis);
		}
		return result;
	}
	
}
