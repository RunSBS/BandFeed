package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
    Optional<Post> findById(UUID id);
    Page<Post> findAllBySongId(UUID songId, Pageable pageable);
    Post save(Post post);
    void delete(Post post);
}
