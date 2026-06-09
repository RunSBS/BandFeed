package com.bandfeed.user_service.infrastructure.entity;

import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.model.UserRole;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder(access = AccessLevel.PRIVATE)
    private UserEntity(Long id, String email, String password, String nickname,
                       String profileImageUrl, String introduction, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
        this.role = role;
    }

    public static UserEntity from(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .nickname(domain.getNickname())
                .profileImageUrl(domain.getProfileImageUrl())
                .introduction(domain.getIntroduction())
                .role(domain.getRole())
                .build();
    }

    public User toDomain() {
        return User.reconstitute(id, email, password, nickname, profileImageUrl,
                introduction, role, getCreatedAt(), getUpdatedAt());
    }

    public void update(User domain) {
        this.password = domain.getPassword();
        this.nickname = domain.getNickname();
        this.profileImageUrl = domain.getProfileImageUrl();
        this.introduction = domain.getIntroduction();
        this.role = domain.getRole();
    }
}
