package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.application.SongService;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.presentation.docs.SongControllerDocs;
import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.InstrumentConfigResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.SongResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.SpotifyTrackResponseDto;
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

    // ── Song 검색/등록 ────────────────────────────────────────────────────────

    @Override
    @GetMapping("/search")
    public ResponseEntity<?> searchSongs(@RequestParam String keyword) {
        List<SpotifyTrackResponseDto> result = songService.searchSpotify(keyword).stream()
                .map(SpotifyTrackResponseDto::from)
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

    // ── InstrumentConfig CRUD ─────────────────────────────────────────────────

    @Override
    @PostMapping("/{songId}/instruments")
    public ResponseEntity<?> addInstrumentConfig(
            @PathVariable UUID songId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request) {
        InstrumentConfig config = songService.addInstrumentConfig(
                songId, request.instrumentType(), request.difficulty(), request.notes(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(InstrumentConfigResponseDto.from(config));
    }

    @Override
    @GetMapping("/{songId}/instruments")
    public ResponseEntity<?> findInstrumentConfigs(@PathVariable UUID songId) {
        List<InstrumentConfig> configs = songService.findInstrumentConfigs(songId);
        return ResponseEntity.ok(configs.stream().map(InstrumentConfigResponseDto::from).toList());
    }

    @Override
    @DeleteMapping("/{songId}/instruments/{configId}")
    public ResponseEntity<?> deleteInstrumentConfig(
            @PathVariable UUID songId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId) {
        songService.deleteInstrumentConfig(configId);
        return ResponseEntity.noContent().build();
    }
}
