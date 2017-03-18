package com.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wang0 on 2016/7/13.
 */
@SpringBootApplication
@EnableScheduling

public class Springboot extends SpringBootServletInitializer {
//    public static void main(String[] args)  {
//        SpringApplication.run(Springboot.class, args);
//    }

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<Springboot> applicationClass = Springboot.class;
}
