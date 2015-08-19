package com.yellowcong.model;
/**
 * 创建用户对象
 * @author 狂飙のyellowcong
 * 2015年8月19日
 *
 */
public class User {
	private int id;
	private String username;
	private String password;
	private Role role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
