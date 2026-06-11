package com.bandfeed.band_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.bandfeed.band_service", "common"})
public class BandServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BandServiceApplication.class, args);
    }
}
