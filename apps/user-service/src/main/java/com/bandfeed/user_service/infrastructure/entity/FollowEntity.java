package com.bandfeed.user_service.infrastructure.entity;

import com.bandfeed.user_service.domain.model.Follow;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID followerId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID followeeId;

    @Builder(access = AccessLevel.PRIVATE)
    private FollowEntity(UUID id, UUID followerId, UUID followeeId, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public static FollowEntity from(Follow domain) {
        return FollowEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .followerId(domain.getFollowerId())
                .followeeId(domain.getFolloweeId())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public Follow toDomain() {
        return Follow.reconstitute(id, followerId, followeeId, getCreatedAt());
    }
}
