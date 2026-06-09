package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);
    List<Post> findAllBySongId(Long songId);
    Post save(Post post);
    void delete(Post post);
}
