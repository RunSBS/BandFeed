package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySongId(Long songId);
}
