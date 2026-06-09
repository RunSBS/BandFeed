package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.docs.TimelinePostControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/timeline-posts")
@RequiredArgsConstructor
public class TimelinePostController implements TimelinePostControllerDocs {

    @Override
    public ResponseEntity<?> createPost(UUID bandId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> getPost(UUID postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> listPosts(UUID bandId, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> updatePost(UUID postId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> deletePost(UUID postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createComment(UUID postId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteComment(UUID commentId) {
        return null;
    }
}
