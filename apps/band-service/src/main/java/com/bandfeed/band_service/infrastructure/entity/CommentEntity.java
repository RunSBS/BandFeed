package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.Comment;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private CommentEntity(Long id, Long postId, Long authorId, String content) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }

    public static CommentEntity from(Comment domain) {
        return CommentEntity.builder()
                .id(domain.getId())
                .postId(domain.getPostId())
                .authorId(domain.getAuthorId())
                .content(domain.getContent())
                .build();
    }

    public Comment toDomain() {
        return Comment.reconstitute(id, postId, authorId, content, getCreatedAt());
    }

    public void update(Comment domain) {
        this.content = domain.getContent();
    }
}
