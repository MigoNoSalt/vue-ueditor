package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example", "com.baidu"})
public class UeditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(UeditorApplication.class, args);
    }
}
