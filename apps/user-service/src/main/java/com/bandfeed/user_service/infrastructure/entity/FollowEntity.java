package com.bandfeed.user_service.infrastructure.entity;

import com.bandfeed.user_service.domain.model.Follow;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID followerId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID followeeId;

    @Builder(access = AccessLevel.PRIVATE)
    private FollowEntity(UUID id, UUID followerId, UUID followeeId) {
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public static FollowEntity from(Follow domain) {
        return FollowEntity.builder()
                .id(domain.getId())
                .followerId(domain.getFollowerId())
                .followeeId(domain.getFolloweeId())
                .build();
    }

    public Follow toDomain() {
        return Follow.reconstitute(id, followerId, followeeId, getCreatedAt());
    }
}
