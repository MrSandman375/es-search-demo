package com.renjie.essearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EsSearchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsSearchDemoApplication.class, args);
    }

}
