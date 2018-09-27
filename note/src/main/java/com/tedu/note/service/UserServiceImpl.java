package com.tedu.note.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.User;
/*
 * 一般以接口名首字母小写为id
 */
@Service("userService")
//@Transactional //类上添加，类中的方法都会被事务管理
public class UserServiceImpl implements UserService{
	//将值注入，jdbc.properties中读取
	@Value("#{db.salt}")
	private String salt;
	
	@Resource(name="userDao")
	private UserDao userDao;
	/*
	 * 一般来看，实现登录功能，如果成功返回数据 如果失败，就抛出自定义异常
	 * 要时刻注意到引用变量属性传值的时候，会不会是null，会报空指针异常（当一个引用类型变量
	 * 值为null的时候，访问属性和方法是就会报错）
	 */
	public User login(String name, String password) throws UserNotFondException, PassWordException {
//		//添加异常
//		String s = null;
//		s.charAt(0);
		
		//查看切面方法的执行时机
		System.out.println("login()");
		//先判断传入的参数是否为空，防止空指针异常 这里password==null||password.trim().isEmpty()不能颠倒
		//因为如果password==null的时候呢，password.trim()会出现空指针异常
		if(password==null||password.trim().isEmpty()) {
			throw new PassWordException("密码空");
		}
		if(name==null||name.trim().isEmpty()) {
			throw new UserNotFondException("用户名为空");
		}
		User user = userDao.findUserByName(name);
		if(user==null) {
			throw new UserNotFondException("用户名错误");
		}
		//密码加密 比对摘要即可 加盐是增加混淆范围，增大安全性
		String pwd = DigestUtils.md5Hex(salt+password.trim());
		if(!pwd.equals(user.getPassword())) {
			throw new PassWordException("密码错误");
		}
		return user;
	}
	
	//验证码验证
	public User regist(String name, String nick, String password, String confirm)
			throws UserNameException, PassWordException {
		//检查name,不能重复
		if(name==null||name.trim().isEmpty()) {
			throw new UserNameException("不能空");
		}
		User one = userDao.findUserByName(name);
		if(one!=null) {
			throw new UserNameException("已重复");
		}
		//检查密码
		if(password==null||password.trim().isEmpty()) {
			throw new PassWordException("不能空");
		}
		if(!password.equals(confirm)) {
			throw new PassWordException("确认密码");
		}
		//检查nick
		if(nick==null||nick.trim().isEmpty()) {
			nick = name;
		}
		//生成id
		String id = UUID.randomUUID().toString();
		String token = "";
		//给密码加盐
		password = DigestUtils.md5Hex(salt+password);
		//封装数据
		User user = new User(id, name, password, token, nick);
		int n = userDao.addUser(user);
		if(n!=1) {
			throw new RuntimeException("添加失败");
		}
		return user;
	}


}
