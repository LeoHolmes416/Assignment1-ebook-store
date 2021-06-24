package com.example.bookshop.filter;

import com.example.bookshop.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthencationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, IOException {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader); //获取以Bearer开头的请求头信息
        System.err.println("authHeader->"+authHeader);
        //存在token
        System.out.println("1. jwtAuthFilter这里");
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {  //判断 是否以指定的字符开头
            System.out.println("2. TokenFilter 拿到指定字符");
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "   把Bearer的前缀去掉
            System.err.println("authToken->"+authToken);
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            System.err.println("username->"+username);
            //token已存在 但未登录
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            if (username != null && authentication1 == null) {
                System.out.println("token已存在但未登录");
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                //验证token是否有校
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    System.out.println("验证token是否有效");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
