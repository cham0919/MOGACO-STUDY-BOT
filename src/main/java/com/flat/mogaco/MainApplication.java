package com.flat.mogacko;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class MainApplication extends SpringBootServletInitializer {
//public class MainApplication {

    public static void main(String[] args){
        SpringApplication.run(MainApplication.class, args);
    }
}