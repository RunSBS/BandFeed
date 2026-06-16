package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.application.SongService;
import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.presentation.docs.SongControllerDocs;
import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.SongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController implements SongControllerDocs {

    private final SongService songService;

    @Override
    @GetMapping("/search")
    public ResponseEntity<?> searchSongs(@RequestParam String keyword) {
        List<SongResponseDto> result = songService.searchSpotify(keyword).stream()
                .map(SongResponseDto::from)
                .toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> registerSong(@RequestBody RegisterSongRequestDto request) {
        Song song = songService.registerSong(request.spotifyTrackId());
        return ResponseEntity.status(HttpStatus.CREATED).body(SongResponseDto.from(song));
    }

    @Override
    @GetMapping("/{songId}")
    public ResponseEntity<?> findSongById(@PathVariable UUID songId) {
        return ResponseEntity.ok(SongResponseDto.from(songService.findSong(songId)));
    }
}
