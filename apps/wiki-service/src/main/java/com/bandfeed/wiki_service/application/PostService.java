package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post createPost(CreatePostCommand command);
    Post findPost(UUID postId);
    Page<Post> findPostsBySong(UUID songId, Pageable pageable);
    Post updatePost(UUID postId, String title, String content, UUID requesterId);
    void deletePost(UUID postId, UUID requesterId);

    InstrumentConfig addInstrumentConfig(UUID postId, String instrumentType, UUID registeredBy);
    List<InstrumentConfig> findInstrumentConfigs(UUID postId);
    void deleteInstrumentConfig(UUID configId);
}
