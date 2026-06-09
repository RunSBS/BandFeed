package com.bandfeed.user_service.infrastructure.entity;

import com.bandfeed.user_service.domain.model.Follow;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long followerId;

    @Column(nullable = false)
    private Long followeeId;

    @Builder(access = AccessLevel.PRIVATE)
    private FollowEntity(Long id, Long followerId, Long followeeId) {
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
