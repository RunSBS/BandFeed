package com.bandfeed.wiki_service.infrastructure.entity;

import com.bandfeed.wiki_service.domain.model.Post;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID songId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID authorId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID bandId;

    @Column(length = 100)
    private String bandName;

    @Column(length = 500)
    private String bandImageUrl;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(UUID id, UUID songId, UUID authorId, UUID bandId, String bandName, String bandImageUrl,
                       String title, String content, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.songId = songId;
        this.authorId = authorId;
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandImageUrl = bandImageUrl;
        this.title = title;
        this.content = content;
    }

    public static PostEntity from(Post domain) {
        return PostEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .songId(domain.getSongId())
                .authorId(domain.getAuthorId())
                .bandId(domain.getBandId())
                .bandName(domain.getBandName())
                .bandImageUrl(domain.getBandImageUrl())
                .title(domain.getTitle())
                .content(domain.getContent())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public Post toDomain() {
        return Post.reconstitute(id, songId, authorId, bandId, bandName, bandImageUrl, title, content,
                getCreatedAt(), getUpdatedAt());
    }

    public void update(Post domain) {
        this.title = domain.getTitle();
        this.content = domain.getContent();
    }
}
