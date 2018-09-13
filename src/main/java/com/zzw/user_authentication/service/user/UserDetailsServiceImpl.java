package com.zzw.user_authentication.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zzw.user_authentication.domain.dao.PermissionDao;
import com.zzw.user_authentication.domain.entity.SysPermission;
import com.zzw.user_authentication.domain.entity.SysUser;
import com.zzw.user_authentication.domain.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * 通过构造器注入UserRepository
	 * 
	 * @param userRepository
	 */
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		List<SysPermission> permissions = permissionDao.findPermissionListByUserName(username);
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		if (permissions != null) {
			for (SysPermission sysPermission : permissions) {
				// 这里设置权限和角色
				authorities.add(new SimpleGrantedAuthority(sysPermission.getPermissionName()));
			}
		}
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
