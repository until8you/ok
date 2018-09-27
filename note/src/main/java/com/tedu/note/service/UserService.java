package com.tedu.note.service;

import com.tedu.note.entity.User;

/*
 * 业务层接口
 */
public interface UserService {
	/*
	 * 登陆功能，登陆成功返回用户信息，登陆失败，抛出异常
	 * @param name 用户名
	 * @param password 密码
	 * @return 如果登陆成功就返回登陆用户信息
	 * 一般来看，实现登录功能，如果成功返回数据 如果失败，就抛出自定义异常 异常不需要做任何操作，只需实现RuntimeException接口
	 * @throws UserNotFoundException 用户不存在
	 * @throws PassWordException 密码错误
	 */
	User login(String name,String password) throws UserNotFondException,PassWordException;
	
	/*
	 * 注册功能
	 * @param name 用户名
	 * @param password 密码
	 * @param nick 昵称
	 * @param confirm 验证密码
	 * @return 注册成功的用户信息
	 * @throws UserNotFoundException 用户名异常
	 * @throws PassWordException 密码错误
	 */
	User regist(String name,String nick,String password,String confirm) throws 
		UserNameException,PassWordException;
}
