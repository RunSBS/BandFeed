package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(CreatePostCommand command);
    Post findPost(Long postId);
    List<Post> findPostsBySong(Long songId);
    Post updatePost(Long postId, String title, String content, Long requesterId);
    void deletePost(Long postId, Long requesterId);
}
