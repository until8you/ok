package com.tedu.note.util;

import java.io.Serializable;
/*
 * 用于封装服务器向客户端返回的数据，包括错误信息和user对象
 */
public class JsonResult implements Serializable{
	
	public static final int SUCCESS = 0;//登陆成功
	public static final int ERROR = 1;//登陆失败
	
	private int status;//登陆状态
	private String message;//错误消息
	private Object data;//返回正确时 的数据
	
	
	public JsonResult() {
		
	}

	public JsonResult(String error) {
		status = ERROR;
		message = error;
	}
	//错误时的构造器
	public JsonResult(Throwable e) {
		status = ERROR;
		message = e.getMessage();
	}
	
	//处理不同状态下的异常构造器
	public JsonResult(int status,Throwable e) {
		this.status = status;
		message = e.getMessage();
	}
	//正确时的构造器
	public JsonResult(Object data) {
		status = SUCCESS;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
