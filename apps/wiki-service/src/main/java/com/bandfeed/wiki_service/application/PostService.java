package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post createPost(CreatePostCommand command);
    Post findPost(UUID postId);
    List<Post> findPostsBySong(UUID songId);
    Post updatePost(UUID postId, String title, String content, UUID requesterId);
    void deletePost(UUID postId, UUID requesterId);
}
