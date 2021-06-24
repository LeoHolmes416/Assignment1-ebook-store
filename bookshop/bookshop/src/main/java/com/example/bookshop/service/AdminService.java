package com.example.bookshop.service;

import com.example.bookshop.bean.RespBean;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
        /**
         * 登陆之后返回token
         * @param username
         * @param password
         * @param request
         * @return
         */
        RespBean login(String username, String password, HttpServletRequest request);
    }
