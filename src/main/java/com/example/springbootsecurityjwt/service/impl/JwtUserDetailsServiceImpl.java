package com.example.springbootsecurityjwt.service.impl;

import com.example.springbootsecurityjwt.domain.Role;
import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.mapper.RoleMapper;
import com.example.springbootsecurityjwt.mapper.UserMapper;
import com.example.springbootsecurityjwt.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName JwtUserDetailsServiceImpl
 * @Description TODO
 * @createTime 2023/11/5
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 从数据库获取用户信息包括用户名、密码和角色信息
        User user = userMapper.selectByUserName(username);
        String password = user.getPassword();
        // 从数据库获取角色用户
        final Integer id = user.getId();
        List<String> roleList = roleMapper.selectByUid(id).stream().map(Role::getName).collect(Collectors.toList());
        return new CustomUserDetails(username, password, roleList);
    }
}
