package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment createTimelinePostComment(UUID postId, UUID authorId, String content);
    List<Comment> findAllTimelinePostComment(UUID postId);
    void deleteTimelinePostComment(UUID commentId, UUID requesterId);
}
