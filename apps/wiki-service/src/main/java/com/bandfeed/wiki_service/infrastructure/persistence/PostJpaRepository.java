package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findAllBySongId(UUID songId, Pageable pageable);

    @Query("SELECT p.songId FROM PostEntity p GROUP BY p.songId ORDER BY COUNT(p) DESC")
    List<UUID> findTopSongIdsByPostCount(Pageable pageable);
}
