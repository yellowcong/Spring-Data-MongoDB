package com.yellowcong.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @author 狂飙のyellowcong
 * 2015年8月20日
 * 
 *
 */

@Document(collection="mongodb_group")
public class Group {
	
	//id设定索引，默认就是id
	@Id
	private int id;
	
	//通过File可以声明里面的 字段
	@Field("group_name")
	private String name;
	
	//引用的对象
	@DBRef
	private User user ;
	
	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Group(int id, String name, User user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
