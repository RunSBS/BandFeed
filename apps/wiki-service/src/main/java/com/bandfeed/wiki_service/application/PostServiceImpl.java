package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.exception.InstrumentConfigNotFoundException;
import com.bandfeed.wiki_service.domain.exception.NotPostAuthorException;
import com.bandfeed.wiki_service.domain.exception.PostNotFoundException;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.repository.InstrumentConfigRepository;
import com.bandfeed.wiki_service.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final InstrumentConfigRepository instrumentConfigRepository;

    @Override
    public Post createPost(CreatePostCommand command) {
        Post post = Post.create(command.songId(), command.authorId(), command.bandId(),
                command.bandName(), command.bandImageUrl(), command.title(), command.content());
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

    @Override
    public InstrumentConfig addInstrumentConfig(UUID postId, String instrumentType, UUID registeredBy) {
        InstrumentConfig config = InstrumentConfig.create(postId, instrumentType, registeredBy);
        return instrumentConfigRepository.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstrumentConfig> findInstrumentConfigs(UUID postId) {
        return instrumentConfigRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteInstrumentConfig(UUID configId) {
        InstrumentConfig config = instrumentConfigRepository.findById(configId)
                .orElseThrow(() -> new InstrumentConfigNotFoundException(configId));
        instrumentConfigRepository.delete(config);
    }
}
