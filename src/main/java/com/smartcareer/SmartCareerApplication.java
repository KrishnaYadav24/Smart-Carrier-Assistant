package com.smartcareer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartCareerApplication {

    public static void main(String[] args) {
        System.out.println("Smart Career Assistant Starting...");
        SpringApplication.run(SmartCareerApplication.class, args);
    }
}