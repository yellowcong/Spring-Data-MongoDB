package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.UserDao;
import com.yellowcong.model.User;

/**
 * 实现
 * @author 狂飙のyellowcong
 * 2015年8月19日
 *
 */

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

}
