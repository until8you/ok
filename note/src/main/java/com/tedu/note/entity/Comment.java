package com.tedu.note.entity;

import java.io.Serializable;

/*»ØÌû*/
public class Comment implements Serializable{

	private static final long serialVersionUID = 4155533761564694391L;
	private Integer id;
	private String title;
	public Comment() {
		super();
	}
	public Comment(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		return id+","+title;
	}
}
