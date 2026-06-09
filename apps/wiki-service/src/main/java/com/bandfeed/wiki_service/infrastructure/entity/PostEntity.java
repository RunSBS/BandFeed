package com.bandfeed.wiki_service.infrastructure.entity;

import com.bandfeed.wiki_service.domain.model.Post;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long songId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(Long id, Long songId, Long authorId, String title, String content) {
        this.id = id;
        this.songId = songId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public static PostEntity from(Post domain) {
        return PostEntity.builder()
                .id(domain.getId())
                .songId(domain.getSongId())
                .authorId(domain.getAuthorId())
                .title(domain.getTitle())
                .content(domain.getContent())
                .build();
    }

    public Post toDomain() {
        return Post.reconstitute(id, songId, authorId, title, content, getCreatedAt(), getUpdatedAt());
    }

    public void update(Post domain) {
        this.title = domain.getTitle();
        this.content = domain.getContent();
    }
}
