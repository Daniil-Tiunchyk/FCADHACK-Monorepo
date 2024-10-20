package com.example.supportfilterservice;

import com.example.supportfilterservice.flink.SupportRequestFilterJob;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupportFilterServiceApplication {

    private final SupportRequestFilterJob filterJob;

    public SupportFilterServiceApplication(SupportRequestFilterJob filterJob) {
        this.filterJob = filterJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(SupportFilterServiceApplication.class, args);
    }

    @PostConstruct
    public void startFlinkJob() {
        new Thread(() -> {
            try {
                filterJob.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
