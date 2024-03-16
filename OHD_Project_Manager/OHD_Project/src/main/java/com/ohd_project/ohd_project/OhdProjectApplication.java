package com.ohd_project.ohd_project;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.ohd_project.ohd_project.mapper")
public class OhdProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OhdProjectApplication.class, args);
    }

}
