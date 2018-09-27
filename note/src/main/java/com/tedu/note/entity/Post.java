package com.tedu.note.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 帖子实体类
 *
 */
public class Post implements Serializable{
	private static final long serialVersionUID = -5168886884541461692L;
	private Integer id;
	private String title;
	
	/*发帖人 多对一*/
	private Person person;
	/*当前帖子收到的回复 一对多*/
	private List<Comment> comments = new ArrayList<Comment>(); //集合时，先初始化，可以避免空指针异常
	public Post() {
		super();
	}
	public Post(Integer id, String title, Person person, List<Comment> comments) {
		super();
		this.id = id;
		this.title = title;
		this.person = person;
		this.comments = comments;
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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public String toString() {
		return id+","+title+","+person+","+comments;
	}
}
