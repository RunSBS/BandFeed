package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
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
        return jpaPostRepository.findById(id);
    }

    @Override
    public List<Post> findAllBySongId(Long songId) {
        return jpaPostRepository.findAllBySongId(songId);
    }

    @Override
    public Post save(Post post) {
        return jpaPostRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        jpaPostRepository.delete(post);
    }
}
