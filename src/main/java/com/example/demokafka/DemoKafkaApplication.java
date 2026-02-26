package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaApplication.class, args);
    }

}
