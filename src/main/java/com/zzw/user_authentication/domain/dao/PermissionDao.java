package com.zzw.user_authentication.domain.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzw.user_authentication.domain.entity.SysPermission;

@Component
public class PermissionDao {

	@Autowired
	private SqlSession sqlSession;

	public List<SysPermission> findPermissionListByUserName(String userName) {
		return sqlSession.selectList("findPermissionListByUserName", userName);
		
		
	}

}
