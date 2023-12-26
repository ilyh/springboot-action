package com.example.springbootsecurityjwt.filter;

import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.service.impl.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret; // 密钥

    @Autowired
    private UserDetailsService jwtUserDetailsService; // 自定义的用户详情服务

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisCacheTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody().getSubject();

            } catch (SignatureException e) {
                // 处理 JWT 签名错误
            } catch (ExpiredJwtException e) {
                // 处理 JWT 过期错误
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final CustomUserDetails user = (CustomUserDetails)redisCacheTemplate.opsForValue().get(username);
            if (jwtTokenIsValid(jwtToken) && user != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean jwtTokenIsValid(String jwtToken) {
        // 在这里，你可以实现自定义的验证逻辑，例如检查 Token 是否过期等。
        // 如果验证通过，返回 true；否则，返回 false。
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
        long expirationTime = claims.getExpiration().getTime();
        long currentTime = System.currentTimeMillis();
        return expirationTime > currentTime;
    }
}
