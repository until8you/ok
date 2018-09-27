package com.tedu.note.entity;

import java.io.Serializable;

/*
 * 笔记本实体类
 */
public class NoteBook implements Serializable {

	private static final long serialVersionUID = 2020870662631919429L;

	private String noteBookId;
	private String userId;
	private String typeId;
	private String name;
	private String desc;
	private String createTime;
	public NoteBook() {
	}
	public NoteBook(String noteBookId, String userId, String typeId, String name, String desc, String createTime) {
		super();
		this.noteBookId = noteBookId;
		this.userId = userId;
		this.typeId = typeId;
		this.name = name;
		this.desc = desc;
		this.createTime = createTime;
	}
	public String getNoteBookId() {
		return noteBookId;
	}
	public void setNoteBookId(String noteBookId) {
		this.noteBookId = noteBookId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((noteBookId == null) ? 0 : noteBookId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteBook other = (NoteBook) obj;
		if (noteBookId == null) {
			if (other.noteBookId != null)
				return false;
		} else if (!noteBookId.equals(other.noteBookId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	public String toString() {
		return noteBookId+","+userId+","+typeId+","+name+","+desc+","+createTime;
		
	}
}
