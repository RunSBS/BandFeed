package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.presentation.docs.PostControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wiki-posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {
}
