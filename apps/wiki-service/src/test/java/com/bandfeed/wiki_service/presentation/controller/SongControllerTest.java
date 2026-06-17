package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyClient;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifySearchResponse;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyTrackItem;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyTrackResponse;
import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.RegisterSongRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.InstrumentConfigResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.PostResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongRepository songRepository;

    @MockitoBean
    private SpotifyClient spotifyClient;

    @Test
    void searchSongs_DB에_없으면_Spotify_호출후_저장() throws Exception {
        String trackId = "track-" + UUID.randomUUID();
        SpotifyTrackItem item = new SpotifyTrackItem(trackId, "한페이지가되어", "海", "Hisaishi", "http://image.url/300", 215000, "http://preview.url");
        Mockito.when(spotifyClient.searchTracks(eq("한페이지"), anyInt(), anyInt()))
                .thenReturn(new SpotifySearchResponse(List.of(item)));

        mockMvc.perform(get("/api/songs").param("keyword", "한페이지"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("한페이지가되어"))
                .andExpect(jsonPath("$.data[0].spotifyTrackId").value(trackId));

        Mockito.verify(spotifyClient).searchTracks(eq("한페이지"), anyInt(), anyInt());
    }

    @Test
    void searchSongs_DB에_있으면_Spotify_호출안함() throws Exception {
        songRepository.save(Song.create("track-cached", "기억의 습작", "전람회", "Forever", "http://image.url/300", 240000));

        mockMvc.perform(get("/api/songs").param("keyword", "기억"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("기억의 습작"));

        Mockito.verifyNoInteractions(spotifyClient);
    }

    @Test
    void registerSong_성공() throws Exception {
        String trackId = "track-register";
        Mockito.when(spotifyClient.getTrack(trackId))
                .thenReturn(new SpotifyTrackResponse(trackId, "Title", "Artist", "Album", "http://img", 200000, "http://preview"));

        RegisterSongRequestDto request = new RegisterSongRequestDto(trackId);

        mockMvc.perform(post("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.spotifyTrackId").value(trackId))
                .andExpect(jsonPath("$.data.title").value("Title"));
    }

@Test
    void findSongById_성공() throws Exception {
        Song song = songRepository.save(Song.create("track-find", "곡명", "아티스트", "앨범", "http://img", 180000));

        mockMvc.perform(get("/api/songs/{songId}", song.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("곡명"));
    }

    @Test
    void instrumentConfig_CRUD_성공() throws Exception {
        Song song = songRepository.save(Song.create("track-instr", "곡명", "아티스트", "앨범", "http://img", 180000));
        UUID userId = UUID.randomUUID();

        // 먼저 post 생성
        CreatePostRequestDto postRequest = new CreatePostRequestDto(song.getId(), null, null, null, "제목", "내용");
        String postResponse = mockMvc.perform(post("/api/wiki-posts")
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        PostResponseDto createdPost = objectMapper.readValue(
                objectMapper.readTree(postResponse).get("data").toString(), PostResponseDto.class);

        AddInstrumentConfigRequestDto request = new AddInstrumentConfigRequestDto("GUITAR");

        String response = mockMvc.perform(post("/api/wiki-posts/{postId}/instruments", createdPost.id())
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.instrumentType").value("GUITAR"))
                .andReturn().getResponse().getContentAsString();

        InstrumentConfigResponseDto created = objectMapper.readValue(
                objectMapper.readTree(response).get("data").toString(), InstrumentConfigResponseDto.class);

        mockMvc.perform(get("/api/wiki-posts/{postId}/instruments", createdPost.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));

        mockMvc.perform(delete("/api/wiki-posts/{postId}/instruments/{configId}", createdPost.id(), created.id())
                        .header("X-User-Id", userId.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/wiki-posts/{postId}/instruments", createdPost.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}
