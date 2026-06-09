package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
    Optional<Post> findById(UUID id);
    List<Post> findAllBySongId(UUID songId);
    Post save(Post post);
    void delete(Post post);
}
