package com.yellowcong.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yellowcong.dao.UserDao;
import com.yellowcong.model.Pager;
import com.yellowcong.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContext.xml")
public class UserDaoTest {
	
	private UserDao  userDao;

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Test
	public void testAdd(){
			for(int i=0;i<20;i++){
				User user = new User();
				user.setPassword("xxx");
				userDao.save(user);
			}
	}
	
	@Test
	public void testLoad(){
		User user = userDao.load(40);
		System.out.println(user.getPassword());
	}
	
	@Test
	public void testDelete(){
		userDao.delete(200);
	}
	
	@Test
	public void  testDelete2(){
	}
	
	@Test
	public void testUpdate(){
		User user = this.userDao.load(39);
		user.setUsername("yellowcong");
		user.setPassword("yellowcong");
		userDao.update(user);
	}
	
	@Test
	public void testList(){
		List<User> users = this.userDao.list();
		for(User user  :users){
			System.out.println(user.getId()+":"+user.getPassword());
		}
	}
	@Test
	public void testQueryByPager(){
//		SystemContext.setPageNow(1);
//		SystemContext.setPageSize(20);
//		SystemContext.setSort("desc");
		Pager<User> pager = this.userDao.queryByPager();
		
		System.out.println("记录条数"+pager.getRowCount());
		System.out.println("页面大小"+pager.getPageSize());
		System.out.println("页面数"+pager.getPageCount());
		System.out.println("当前所在页面"+pager.getPageNow());
		List<User> users = pager.getData();
		for(User user:users){
			System.out.println(user.getId());
		}
	}
	
	@Test
	public void testQueryByPager2(){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").gt(6));
		
	Pager<User> pager = this.userDao.queryByPager(query);
		
		System.out.println("记录条数"+pager.getRowCount());
		System.out.println("页面大小"+pager.getPageSize());
		System.out.println("页面数"+pager.getPageCount());
		System.out.println("当前所在页面"+pager.getPageNow());
		List<User> users = pager.getData();
		for(User user:users){
			System.out.println(user.getId());
		}
	}
}
