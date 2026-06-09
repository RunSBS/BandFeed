package com.bandfeed.user_service.domain.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private String email;
    private String password;
    private String nickname;
    private String profileImageUrl;
    private String introduction;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password, String nickname, String profileImageUrl) {
        this.id = null;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = null;
        this.role = UserRole.USER;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private User(Long id, String email, String password, String nickname, String profileImageUrl,
                 String introduction, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(String email, String password, String nickname, String profileImageUrl) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public static User reconstitute(Long id,
                                    String email,
                                    String password,
                                    String nickname,
                                    String profileImageUrl,
                                    String introduction,
                                    UserRole role,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {
        return new User(
                id,
                email,
                password,
                nickname,
                profileImageUrl,
                introduction,
                role,
                createdAt,
                updatedAt);
    }

    public void updateProfile(String nickname, String profileImageUrl, String introduction) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public void changePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }
}
