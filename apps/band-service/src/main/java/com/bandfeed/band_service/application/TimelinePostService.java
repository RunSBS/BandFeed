package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;
import java.util.UUID;

public interface TimelinePostService {

    TimelinePost createPost(UUID bandId, UUID authorId, String title, String content);
    TimelinePost findPost(UUID postId);
    List<TimelinePost> findPosts(UUID bandId);
    TimelinePost updatePost(UUID postId, String title, String content, UUID requesterId);
    void deletePost(UUID postId, UUID requesterId);
}
