package com.sparta.springboardprac1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBoardPrac1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoardPrac1Application.class, args);
    }

}
