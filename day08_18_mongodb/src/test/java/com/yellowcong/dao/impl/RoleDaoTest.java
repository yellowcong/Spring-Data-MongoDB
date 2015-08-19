package com.yellowcong.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.dao.RoleDao;
import com.yellowcong.dao.UserDao;
import com.yellowcong.model.Role;
import com.yellowcong.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class RoleDaoTest {
	private RoleDao  roleDao;
	private UserDao  userDao;

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Resource(name = "roleDao")
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	@Test
	public void testAdd(){
		//添加配置
		Logger.getLogger(SpringJUnit4ClassRunner.class); 
		Role role = new Role();
		role.setName("管理员");
		this.roleDao.save(role);
	}
	
	@Test
	public void testAddRole(){
		User user = new User();
		user.setUsername("admin");
		user.setPassword("yellowcong");
		user.setRole(this.roleDao.load(1));
		this.userDao.save(user);
	}

	
}
