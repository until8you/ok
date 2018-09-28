package com.redis;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {

		Jedis jedis = new Jedis("localhost",6379);
		int i = 0;
		try {
			long start = System.currentTimeMillis();
			while(true) {
				long end = System.currentTimeMillis();
				if(end-start>=1000) {
					break;
				}
				i++;
				jedis.set("test"+i, i+"");
			}
		} finally {
			jedis.close();
		}
		System.err.println("redisÃ¿Ãë²Ù×÷£º"+i+"´Î");
	}

}
