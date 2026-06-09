package com.bandfeed.chat_service.infrastructure.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    UserInfo getUser(@PathVariable Long userId);

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class UserInfo {
        private Long id;
        private String nickname;
    }
}
