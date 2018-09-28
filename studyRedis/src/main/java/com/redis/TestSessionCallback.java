package com.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

public class TestSessionCallback {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = ac.getBean(RedisTemplate.class);
		final Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("role_note_1");
		SessionCallback callback = new SessionCallback<Role>() {

			public Role execute(RedisOperations ops) throws DataAccessException {
				ops.boundValueOps("role_1").set(role);
				return (Role)ops.boundValueOps("role_1").get();
			}
		};
		Role saveRole = (Role)redisTemplate.execute(callback);
		System.out.println(saveRole.getId());
	}
}
