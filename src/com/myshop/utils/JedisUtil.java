package com.myshop.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class JedisUtil {
	private static JedisPool pool;
	static{
		//读取配置文件获取配置信息
		ResourceBundle bundle = ResourceBundle.getBundle("jedisconfig");
		String host = bundle.getString("host");
		Integer port = Integer.parseInt(bundle.getString("port"));
		Integer maxIdle = Integer.parseInt(bundle.getString("maxIdle"));
		Integer maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
		JedisPoolConfig config = new JedisPoolConfig();
		
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		
		pool = new JedisPool(config, host, port);
	}
	public static Jedis getJedis(){
		//从jedis连接池中获取jedis
		return pool.getResource();
	}
}
