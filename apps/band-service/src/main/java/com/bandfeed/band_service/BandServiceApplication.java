package com.bandfeed.band_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BandServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BandServiceApplication.class, args);
    }
}
