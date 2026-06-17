package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.exception.CommentNotFoundException;
import com.bandfeed.band_service.domain.exception.NotCommentAuthorException;
import com.bandfeed.band_service.domain.exception.NotPostAuthorException;
import com.bandfeed.band_service.domain.exception.TimelinePostNotFoundException;
import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
import com.bandfeed.band_service.domain.repository.CommentRepository;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
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
public class TimelinePostServiceImpl implements TimelinePostService {

    private final TimelinePostRepository timelinePostRepository;
    private final CommentRepository commentRepository;
    private final BandMemberRepository bandMemberRepository;

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
                .orElseThrow(() -> new TimelinePostNotFoundException(postId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimelinePost> findAllTimelinePost(UUID bandId, Pageable pageable) {
        return timelinePostRepository.findAllByBandId(bandId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimelinePost> findFeed(UUID userId, Pageable pageable) {
        List<UUID> bandIds = bandMemberRepository.findAllByUserId(userId).stream()
                .map(BandMember::getBandId)
                .toList();
        return timelinePostRepository.findAllByBandIdIn(bandIds, pageable);
    }

    @Override
    public TimelinePost updateTimelinePostInfo(UUID postId, String title, String content, UUID requesterId) {
        TimelinePost post = findTimelinePostById(postId);
        if (!post.getAuthorId().equals(requesterId)) {
            throw new NotPostAuthorException(requesterId);
        }
        post.update(title, content);
        return timelinePostRepository.save(post);
    }

    @Override
    public void deleteTimelinePost(UUID postId, UUID requesterId) {
        TimelinePost post = findTimelinePostById(postId);
        if (!post.getAuthorId().equals(requesterId)) {
            throw new NotPostAuthorException(requesterId);
        }
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
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!comment.getAuthorId().equals(requesterId)) {
            throw new NotCommentAuthorException(requesterId);
        }
        commentRepository.delete(comment);
    }
}
