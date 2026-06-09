package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findPostsBySong(Long songId) {
        return postRepository.findAllBySongId(songId);
    }

    @Override
    public Post updatePost(Long postId, String title, String content, Long requesterId) {
        Post post = findPost(postId);
        post.update(title, content);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, Long requesterId) {
        Post post = findPost(postId);
        postRepository.delete(post);
    }
}
