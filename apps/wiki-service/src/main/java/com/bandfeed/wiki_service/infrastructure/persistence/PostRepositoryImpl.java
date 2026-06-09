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

    private final JpaPostRepository jpaPostRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return jpaPostRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public List<Post> findAllBySongId(Long songId) {
        return jpaPostRepository.findAllBySongId(songId).stream().map(PostEntity::toDomain).toList();
    }

    @Override
    public Post save(Post post) {
        return jpaPostRepository.save(PostEntity.from(post)).toDomain();
    }

    @Override
    public void delete(Post post) {
        jpaPostRepository.deleteById(post.getId());
    }
}
