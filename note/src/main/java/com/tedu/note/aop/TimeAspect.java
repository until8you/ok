package com.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * 对软件业务进行性能测试
 *
 */
@Component
@Aspect
public class TimeAspect {

	@Around("bean(*Service)")
	public Object time(ProceedingJoinPoint jp) throws Throwable{
		
		long t1 = System.currentTimeMillis();
		Object val = jp.proceed();
		long t2 = System.currentTimeMillis();
		//JointPoint 对象可以获取目标业务方法的详细信息 包括方法签名 调用参数等
		//Signature:签名 方法签名
		Signature m = jp.getSignature();
		System.out.println(m+"用时："+(t2-t1));
		return val;
		
	}
	
}
