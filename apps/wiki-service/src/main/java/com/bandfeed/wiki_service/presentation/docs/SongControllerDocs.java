package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface SongControllerDocs {

    // ── Song 검색/등록 ────────────────────────────────────────────────────────

    @GetMapping("/search")
    ResponseEntity<?> searchSongs(@RequestParam String keyword);

    @PostMapping
    ResponseEntity<?> registerSong(@RequestBody RegisterSongRequestDto request);

    @GetMapping("/{songId}")
    ResponseEntity<?> findSongById(@PathVariable UUID songId);

    // ── InstrumentConfig CRUD ─────────────────────────────────────────────────

    @PostMapping("/{songId}/instruments")
    ResponseEntity<?> addInstrumentConfig(
            @PathVariable UUID songId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request);

    @GetMapping("/{songId}/instruments")
    ResponseEntity<?> findInstrumentConfigs(@PathVariable UUID songId);

    @DeleteMapping("/{songId}/instruments/{configId}")
    ResponseEntity<?> deleteInstrumentConfig(
            @PathVariable UUID songId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId);
}
