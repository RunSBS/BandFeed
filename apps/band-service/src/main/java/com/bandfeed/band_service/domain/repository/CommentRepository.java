package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(UUID id);
    List<Comment> findAllByPostId(UUID postId);
    void delete(Comment comment);
}
