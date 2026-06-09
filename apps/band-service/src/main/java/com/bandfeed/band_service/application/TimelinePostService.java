package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;

public interface TimelinePostService {

    TimelinePost createPost(Long bandId, Long authorId, String title, String content);
    TimelinePost findPost(Long postId);
    List<TimelinePost> findPosts(Long bandId);
    TimelinePost updatePost(Long postId, String title, String content, Long requesterId);
    void deletePost(Long postId, Long requesterId);
}
