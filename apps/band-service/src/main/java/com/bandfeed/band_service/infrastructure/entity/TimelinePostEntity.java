package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.TimelinePost;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "timeline_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimelinePostEntity extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID bandId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID authorId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private TimelinePostEntity(UUID id, UUID bandId, UUID authorId, String title, String content) {
        this.id = id;
        this.bandId = bandId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public static TimelinePostEntity from(TimelinePost domain) {
        return TimelinePostEntity.builder()
                .id(domain.getId())
                .bandId(domain.getBandId())
                .authorId(domain.getAuthorId())
                .title(domain.getTitle())
                .content(domain.getContent())
                .build();
    }

    public TimelinePost toDomain() {
        return TimelinePost.reconstitute(id, bandId, authorId, title, content, getCreatedAt(), getUpdatedAt());
    }

    public void update(TimelinePost domain) {
        this.title = domain.getTitle();
        this.content = domain.getContent();
    }
}
