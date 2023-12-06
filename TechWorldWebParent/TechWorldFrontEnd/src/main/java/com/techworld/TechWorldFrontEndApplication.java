package com.techworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.techworld.common.entity","com.techworld"})
public class TechWorldFrontEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechWorldFrontEndApplication.class, args);
    }

}
