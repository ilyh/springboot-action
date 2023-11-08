package com.example.springbootsecurityjwt.service;

import com.example.springbootsecurityjwt.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName JwtUserDetailsService
 * @Description TODO
 * @createTime 2023/11/5
 */
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
