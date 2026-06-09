package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
import com.bandfeed.wiki_service.infrastructure.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository jpa;

    @Override
    public Optional<Post> findById(UUID id) {
        return jpa.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public List<Post> findAllBySongId(UUID songId) {
        return jpa.findAllBySongId(songId).stream().map(PostEntity::toDomain).toList();
    }

    @Override
    public Post save(Post post) {
        return jpa.save(PostEntity.from(post)).toDomain();
    }

    @Override
    public void delete(Post post) {
        jpa.deleteById(post.getId());
    }
}
