package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.exception.NotPostAuthorException;
import com.bandfeed.wiki_service.domain.exception.PostNotFoundException;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(CreatePostCommand command) {
        Post post = Post.create(command.songId(), command.authorId(), command.title(), command.content());
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPost(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findPostsBySong(UUID songId, Pageable pageable) {
        return postRepository.findAllBySongId(songId, pageable);
    }

    @Override
    public Post updatePost(UUID postId, String title, String content, UUID requesterId) {
        Post post = findPost(postId);
        if (!post.getAuthorId().equals(requesterId)) {
            throw new NotPostAuthorException(requesterId);
        }
        post.update(title, content);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(UUID postId, UUID requesterId) {
        Post post = findPost(postId);
        if (!post.getAuthorId().equals(requesterId)) {
            throw new NotPostAuthorException(requesterId);
        }
        postRepository.delete(post);
    }
}
