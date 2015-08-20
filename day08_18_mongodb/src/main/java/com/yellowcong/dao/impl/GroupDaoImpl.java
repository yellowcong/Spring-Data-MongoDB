package com.yellowcong.dao.impl;


import org.springframework.stereotype.Repository;

import com.yellowcong.dao.GroupDao;
import com.yellowcong.model.Group;


@Repository("groupDao")
public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao{
	
}
