package com.example.serviceprodvider1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceProdvider1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProdvider1Application.class, args);
    }

}
