package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
    List<PostEntity> findAllBySongId(UUID songId);
}
