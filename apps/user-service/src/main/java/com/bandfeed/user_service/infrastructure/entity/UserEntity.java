package com.bandfeed.user_service.infrastructure.entity;

import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.model.UserRole;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

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
    private UserEntity(UUID id, String email, String password, String nickname,
                       String profileImageUrl, String introduction, UserRole role, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
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
                .isNew(!domain.isPersisted())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .nickname(domain.getNickname())
                .profileImageUrl(domain.getProfileImageUrl())
                .introduction(domain.getIntroduction())
                .role(domain.getRole())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

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
