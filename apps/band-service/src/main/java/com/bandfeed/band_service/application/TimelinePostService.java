package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;
import java.util.UUID;

public interface TimelinePostService {

    // ── TimelinePost CRUD ─────────────────────────────────────────────────────
    TimelinePost createTimelinePost(UUID bandId, UUID authorId, String title, String content);
    TimelinePost findTimelinePostById(UUID postId);
    List<TimelinePost> findAllTimelinePost(UUID bandId);
    TimelinePost updateTimelinePostInfo(UUID postId, String title, String content, UUID requesterId);
    void deleteTimelinePost(UUID postId, UUID requesterId);

    // ── Comment CRUD ──────────────────────────────────────────────────────────
    Comment createTimelinePostComment(UUID postId, UUID authorId, String content);
    List<Comment> findAllTimelinePostComment(UUID postId);
    void deleteTimelinePostComment(UUID commentId, UUID requesterId);
}
