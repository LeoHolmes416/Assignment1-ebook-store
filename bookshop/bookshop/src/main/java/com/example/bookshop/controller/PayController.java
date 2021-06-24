package com.example.bookshop.controller;

import com.example.bookshop.bean.RespBean;
import com.example.bookshop.entity.UB;
import com.example.bookshop.mapper.UBMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
@Api(tags = "PayController", value = "支付控制器")
public class PayController {
    @Autowired
    UBMapper ubMapper;

    @PostMapping("/")
    @ApiOperation(value = "测试购买书本")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", dataType = "Integer"),
            @ApiImplicitParam(name = "bid", dataType = "Integer"),
    }
    )
    public RespBean pay(UB ub) {
        int i = ubMapper.findDuplicate(ub);
        if (i > 0) {
            return RespBean.error("已购买");
        }
        ubMapper.insert(ub);
        return RespBean.success("成功");
    }


}
