package com.site;

/**
 * Created by wang0 on 2016/7/13.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Springboot {
    public static void main(String[] args) {
        SpringApplication.run(Springboot.class, args);
    }
}
/**
 * 移除内嵌tomcat时
 */
//@SpringBootApplication
//@EnableScheduling
//public class Springboot extends SpringBootServletInitializer {
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(applicationClass, args);
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(applicationClass);
//    }
//
//    private static Class<Springboot> applicationClass = Springboot.class;
//}
