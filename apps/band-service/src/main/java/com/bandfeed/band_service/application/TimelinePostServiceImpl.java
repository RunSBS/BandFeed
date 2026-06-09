package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.CommentRepository;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimelinePostServiceImpl implements TimelinePostService {

    private final TimelinePostRepository timelinePostRepository;
    private final CommentRepository commentRepository;

    // ── TimelinePost CRUD ─────────────────────────────────────────────────────

    @Override
    public TimelinePost createTimelinePost(UUID bandId, UUID authorId, String title, String content) {
        TimelinePost post = TimelinePost.create(bandId, authorId, title, content);
        return timelinePostRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public TimelinePost findTimelinePostById(UUID postId) {
        return timelinePostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("TimelinePost not found: " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimelinePost> findAllTimelinePost(UUID bandId) {
        return timelinePostRepository.findAllByBandId(bandId);
    }

    @Override
    public TimelinePost updateTimelinePostInfo(UUID postId, String title, String content, UUID requesterId) {
        TimelinePost post = findTimelinePostById(postId);
        post.update(title, content);
        return timelinePostRepository.save(post);
    }

    @Override
    public void deleteTimelinePost(UUID postId, UUID requesterId) {
        TimelinePost post = findTimelinePostById(postId);
        timelinePostRepository.delete(post);
    }

    // ── Comment CRUD ──────────────────────────────────────────────────────────

    @Override
    public Comment createTimelinePostComment(UUID postId, UUID authorId, String content) {
        Comment comment = Comment.create(postId, authorId, content);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllTimelinePostComment(UUID postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteTimelinePostComment(UUID commentId, UUID requesterId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));
        commentRepository.delete(comment);
    }
}
