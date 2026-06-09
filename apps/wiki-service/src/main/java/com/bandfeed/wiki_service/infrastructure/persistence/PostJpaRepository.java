package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllBySongId(Long songId);
}
