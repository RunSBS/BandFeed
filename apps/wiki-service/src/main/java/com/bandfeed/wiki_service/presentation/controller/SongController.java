package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.application.SongService;
import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.presentation.docs.SongControllerDocs;
import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.SongResponseDto;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
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
    @GetMapping
    public ResponseEntity<CommonResponse<List<SongResponseDto>>> findSongs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "10") int limit) {
        List<SongResponseDto> result;
        if (keyword != null && !keyword.isBlank()) {
            result = songService.searchSpotify(keyword).stream().map(SongResponseDto::from).toList();
        } else if ("popular".equalsIgnoreCase(sort)) {
            result = songService.findPopularSongs(limit).stream().map(SongResponseDto::from).toList();
        } else {
            result = List.of();
        }
        return CommonResponse.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<SongResponseDto>> registerSong(@RequestBody RegisterSongRequestDto request) {
        Song song = songService.registerSong(request.spotifyTrackId());
        return CommonResponse.created("곡이 등록되었습니다.", SongResponseDto.from(song));
    }

    @Override
    @GetMapping("/{songId}")
    public ResponseEntity<CommonResponse<SongResponseDto>> findSongById(@PathVariable UUID songId) {
        return CommonResponse.ok(SongResponseDto.from(songService.findSong(songId)));
    }
}
