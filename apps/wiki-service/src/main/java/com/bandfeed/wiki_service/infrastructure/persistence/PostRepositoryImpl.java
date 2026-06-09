package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
import com.bandfeed.wiki_service.infrastructure.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository jpa;

    @Override
    public Optional<Post> findById(Long id) {
        return jpa.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public List<Post> findAllBySongId(Long songId) {
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
