package com.yellowcong.dao.impl;

import org.springframework.stereotype.Repository;

import com.yellowcong.dao.RoleDao;
import com.yellowcong.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

}
