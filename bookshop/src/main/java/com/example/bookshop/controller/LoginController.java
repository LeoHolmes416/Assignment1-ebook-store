package com.example.bookshop.controller;

import com.example.bookshop.bean.RespBean;
import com.example.bookshop.entity.User;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Api(tags = "UmsAdminController", value = "后台用户管理")
public class LoginController {
    @Autowired
    private AdminService iAdminService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public RespBean login(User adminLoginParam, HttpServletRequest request) {
        System.out.println("LoginController");
        return iAdminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request);
    }

    /**
     * 获取当前用户登陆信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登陆用户信息")
    @RequestMapping("/admin/info")
    public RespBean getAdmininfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        System.err.println("查看用户信息->" + username);
        User user = userMapper.findByUsername(username);
        user.setPassword(null);
        return RespBean.success("成功", user);
    }


    @ApiOperation(value = "退出登陆")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");
    }


    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}

