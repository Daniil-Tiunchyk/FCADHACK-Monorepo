package com.example.supportfilterservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SupportFilterServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(SupportFilterServiceApplication.class, args);
    }


}
