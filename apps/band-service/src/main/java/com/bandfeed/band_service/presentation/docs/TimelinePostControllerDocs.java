package com.bandfeed.band_service.presentation.docs;

import org.springframework.http.ResponseEntity;

public interface TimelinePostControllerDocs {
    ResponseEntity<?> createPost(Long bandId, Object request);

    ResponseEntity<?> getPost(Long postId);

    ResponseEntity<?> listPosts(Long bandId, int page, int size);

    ResponseEntity<?> updatePost(Long postId, Object request);

    ResponseEntity<?> deletePost(Long postId);

    ResponseEntity<?> createComment(Long postId, Object request);

    ResponseEntity<?> deleteComment(Long commentId);
}
