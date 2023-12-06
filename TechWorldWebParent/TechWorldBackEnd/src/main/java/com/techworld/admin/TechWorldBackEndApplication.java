package com.techworld.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.techworld.common.entity", "com.techworld.admin.user"})
public class TechWorldBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechWorldBackEndApplication.class, args);
    }

}
