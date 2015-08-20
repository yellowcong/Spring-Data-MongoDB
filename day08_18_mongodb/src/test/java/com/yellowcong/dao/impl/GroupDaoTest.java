package com.yellowcong.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.dao.GroupDao;
import com.yellowcong.dao.UserDao;
import com.yellowcong.model.Group;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class GroupDaoTest {
	private GroupDao groupDao;
	private UserDao  userDao;
	
	
	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource(name = "groupDao")
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Test
	public void testAdd(){
		Group group = new Group();
		group.setName("yellowcong");
		this.groupDao.save(group);
	}
	
	@Test
	public void testGroupDBRef(){
		Group group = new Group();
		group.setName("yellowcong");
		group.setUser(this.userDao.load(1));
		
		this.groupDao.save(group);
	}
	
	@Test
	public void testLoad(){
		Group group = this.groupDao.load(3);
		System.out.println(group.getName());
		System.out.println(group.getUser().getPassword());
	}
	
	
}
