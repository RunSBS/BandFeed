package com.bandfeed.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.config.client.ConfigClientAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication(exclude = {
        ConfigClientAutoConfiguration.class,
        RefreshAutoConfiguration.class
})
public class ConfigServerApplication {

    public static void main(String[] args) {
        // config server 자신이 config client로 동작하지 않도록
        // Spring이 어떤 것도 읽기 전에 시스템 프로퍼티로 비활성화
        System.setProperty("spring.cloud.config.enabled", "false");
        System.setProperty("spring.cloud.config.import-check.enabled", "false");
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
