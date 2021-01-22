package com.example.learn.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.learn.demo.dao")
public class Learn1Application {

    public static void main(String[] args) {
        SpringApplication.run(Learn1Application.class, args);
    }

}
