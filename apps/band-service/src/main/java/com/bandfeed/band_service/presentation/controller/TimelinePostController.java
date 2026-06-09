package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.docs.TimelinePostControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timeline-posts")
@RequiredArgsConstructor
public class TimelinePostController implements TimelinePostControllerDocs {

    @Override
    public ResponseEntity<?> createPost(Long bandId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> getPost(Long postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> listPosts(Long bandId, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> updatePost(Long postId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> deletePost(Long postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createComment(Long postId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentId) {
        return null;
    }
}
