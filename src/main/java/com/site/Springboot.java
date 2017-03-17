package com.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wang0 on 2016/7/13.
 */
@SpringBootApplication
@EnableScheduling

public class Springboot {
    public static void main(String[] args)  {
        SpringApplication.run(Springboot.class, args);
    }
}
