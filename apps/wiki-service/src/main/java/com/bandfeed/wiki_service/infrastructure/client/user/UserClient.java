package com.bandfeed.wiki_service.infrastructure.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    UserInfo getUser(@PathVariable UUID userId);

    @GetMapping("/api/users/batch")
    List<UserInfo> getUsersByIds(@RequestParam List<UUID> ids);

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class UserInfo {
        private UUID id;
        private String nickname;
        private String profileImageUrl;
    }
}
