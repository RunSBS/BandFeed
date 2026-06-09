package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimelinePostServiceImpl implements TimelinePostService {

    private final TimelinePostRepository timelinePostRepository;

    @Override
    public TimelinePost createPost(Long bandId, Long authorId, String title, String content) {
        TimelinePost post = TimelinePost.create(bandId, authorId, title, content);
        return timelinePostRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public TimelinePost findPost(Long postId) {
        return timelinePostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("TimelinePost not found: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimelinePost> findPosts(Long bandId) {
        return timelinePostRepository.findAllByBandId(bandId);
    }

    @Override
    public TimelinePost updatePost(Long postId, String title, String content, Long requesterId) {
        TimelinePost post = findPost(postId);
        post.update(title, content);
        return timelinePostRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, Long requesterId) {
        TimelinePost post = findPost(postId);
        timelinePostRepository.delete(post);
    }
}
