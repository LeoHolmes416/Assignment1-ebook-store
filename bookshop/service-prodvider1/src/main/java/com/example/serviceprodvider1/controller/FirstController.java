package com.example.serviceprodvider1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    @RequestMapping("/query")
    @ResponseBody
    public ViewVo query() {
        ViewVo vo = new ViewVo();
        vo.setName("hoho");
        vo.setDecription("with dgold 123");
        return vo;
    }
}