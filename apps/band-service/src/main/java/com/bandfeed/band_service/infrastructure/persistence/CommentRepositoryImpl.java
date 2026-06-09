package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.repository.CommentRepository;
import com.bandfeed.band_service.infrastructure.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository jpa;

    @Override
    public Comment save(Comment comment) {
        return jpa.save(CommentEntity.from(comment)).toDomain();
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        return jpa.findById(id).map(CommentEntity::toDomain);
    }

    @Override
    public List<Comment> findAllByPostId(UUID postId) {
        return jpa.findAllByPostId(postId).stream().map(CommentEntity::toDomain).toList();
    }

    @Override
    public void delete(Comment comment) {
        jpa.deleteById(comment.getId());
    }
}
