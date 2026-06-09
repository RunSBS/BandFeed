package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.presentation.docs.SongControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController implements SongControllerDocs {
}
