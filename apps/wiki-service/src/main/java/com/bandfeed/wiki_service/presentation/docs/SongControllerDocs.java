package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.SongResponseDto;
import common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface SongControllerDocs {

    @GetMapping
    ResponseEntity<CommonResponse<List<SongResponseDto>>> findSongs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "10") int limit);

    @PostMapping
    ResponseEntity<CommonResponse<SongResponseDto>> registerSong(@RequestBody RegisterSongRequestDto request);

    @GetMapping("/{songId}")
    ResponseEntity<CommonResponse<SongResponseDto>> findSongById(@PathVariable UUID songId);
}
