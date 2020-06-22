package com.jb.xjjreptile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class XjjReptileApplication {

    public static void main(String[] args) {
        SpringApplication.run(XjjReptileApplication.class, args);
    }

}
