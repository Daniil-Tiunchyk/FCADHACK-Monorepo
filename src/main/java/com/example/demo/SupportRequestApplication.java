package com.example.demo;

import com.example.demo.service.SupportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupportRequestApplication implements CommandLineRunner {

    @Autowired
    private SupportRequestService supportRequestService;

    public static void main(String[] args) {
        SpringApplication.run(SupportRequestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        supportRequestService.loadData();
    }
}
