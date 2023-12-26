package com.example.springbootsecurityjwt.service.impl;

import com.example.springbootsecurityjwt.domain.Role;
import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.mapper.RoleMapper;
import com.example.springbootsecurityjwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName JwtUserDetailsServiceImpl
 * @Description TODO
 * @createTime 2023/11/5
 */
@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        // 从数据库获取用户信息包括用户名、密码和角色信息
        User user = userMapper.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // String password = user.getPassword();
        // // 从数据库获取角色用户
        // final Integer id = user.getId();
        // List<String> roleList = roleMapper.selectByUid(id).stream().map(Role::getName).collect(Collectors.toList());
        // return new CustomUserDetails(username, password, roleList);
        List<String> roleList = Arrays.asList("test", "admin");
        return new CustomUserDetails(username, user.getPassword(), roleList);
    }
}
