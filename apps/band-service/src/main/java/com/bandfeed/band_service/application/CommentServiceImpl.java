package com.bandfeed.band_service.application;

import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.repository.CommentRepository;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

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
