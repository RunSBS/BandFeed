package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface SongControllerDocs {

    @GetMapping("/search")
    ResponseEntity<?> searchSongs(@RequestParam String keyword);

    @PostMapping
    ResponseEntity<?> registerSong(@RequestBody RegisterSongRequestDto request);

    @GetMapping("/{songId}")
    ResponseEntity<?> findSongById(@PathVariable UUID songId);
}
