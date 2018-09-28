package com.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class TestSpringRedis {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = ac.getBean(RedisTemplate.class);
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");
		redisTemplate.opsForValue().set("role_1", role);
		Role role1 = (Role)redisTemplate.opsForValue().get("role_1");
		System.out.println(role1.getRoleName());
	}
}
