package com.example.servicecustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientControlelr {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/queryService")
    @ResponseBody
    public ViewVo query() {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-prodvider1");
        StringBuilder urls = new StringBuilder();
        for (ServiceInstance instance : instances) {
            urls.append(instance.getHost() + ":" + instance.getPort()).append(",");
        }
        ViewVo vo = new ViewVo();
        vo.setName("service-prodvider1");
        vo.setDecription(urls.toString());
        return vo;
    }
}