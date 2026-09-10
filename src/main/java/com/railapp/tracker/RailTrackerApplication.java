package com.railapp.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RailTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RailTrackerApplication.class, args);
    }
}
