package com.bandfeed.band_service.presentation.docs;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface TimelinePostControllerDocs {
    ResponseEntity<?> createPost(UUID bandId, Object request);

    ResponseEntity<?> getPost(UUID postId);

    ResponseEntity<?> listPosts(UUID bandId, int page, int size);

    ResponseEntity<?> updatePost(UUID postId, Object request);

    ResponseEntity<?> deletePost(UUID postId);

    ResponseEntity<?> createComment(UUID postId, Object request);

    ResponseEntity<?> deleteComment(UUID commentId);
}
