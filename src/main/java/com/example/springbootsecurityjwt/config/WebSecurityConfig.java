package com.example.springbootsecurityjwt.config;

import com.example.springbootsecurityjwt.filter.JwtTokenFilter;
import com.example.springbootsecurityjwt.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @createTime 2023/11/6
 */
@Configuration
@EnableWebSecurity	// 添加 security 过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证
public class WebSecurityConfig {

    @Resource(name = "jwtUserDetailsServiceImpl")
    private UserDetailsService jwtUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/demo/authentication/*").permitAll()
                        .antMatchers(HttpMethod.POST).authenticated()
                        .antMatchers(HttpMethod.PUT).authenticated()
                        .antMatchers(HttpMethod.DELETE).authenticated()
                        .antMatchers(HttpMethod.GET).authenticated()
                )
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 配置 AuthenticationManager，用于处理身份验证
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // return super.authenticationManagerBean();
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/demo/authentication/*");
    }
}
