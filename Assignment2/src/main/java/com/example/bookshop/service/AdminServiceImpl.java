package com.example.bookshop.service;

import com.example.bookshop.bean.RespBean;
import com.example.bookshop.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl  implements AdminService{

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private  String tokenHead;


    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码错误");
        }
        //更新security登陆用户对象
        Object userDetails1;
        Object principal;
        UsernamePasswordAuthenticationToken authenticationToken =new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token =jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap =new HashMap<>();
        tokenMap.put("token",token);
        System.err.println("token----->"+token);
        tokenMap.put("tokenHead",tokenHead);
        System.err.println("tokenHead------->"+tokenHead);
        return RespBean.success("登陆成功",tokenMap);
    }
}
