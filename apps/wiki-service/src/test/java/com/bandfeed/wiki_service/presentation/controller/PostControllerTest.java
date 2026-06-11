package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.PostResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongRepository songRepository;

    @Test
    void createAndFindPost_성공() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        PostResponseDto created = createPost(songId, authorId, "이 곡 합주 팁", "이렇게 치면 편해요");

        mockMvc.perform(get("/api/wiki-posts/{postId}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("이 곡 합주 팁"))
                .andExpect(jsonPath("$.content").value("이렇게 치면 편해요"))
                .andExpect(jsonPath("$.songId").value(songId.toString()));
    }

    @Test
    void findAllPostsBySong_성공() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        createPost(songId, authorId, "글1", "내용1");
        createPost(songId, authorId, "글2", "내용2");

        mockMvc.perform(get("/api/wiki-posts").param("songId", songId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void findAllPostsBySong_최신순_정렬() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        createPost(songId, authorId, "먼저쓴글", "내용1");
        createPost(songId, authorId, "나중쓴글", "내용2");

        mockMvc.perform(get("/api/wiki-posts")
                        .param("songId", songId.toString())
                        .param("sort", "latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("나중쓴글"))
                .andExpect(jsonPath("$.content[1].title").value("먼저쓴글"));
    }

    @Test
    void findAllPostsBySong_오래된순_정렬() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        createPost(songId, authorId, "먼저쓴글", "내용1");
        createPost(songId, authorId, "나중쓴글", "내용2");

        mockMvc.perform(get("/api/wiki-posts")
                        .param("songId", songId.toString())
                        .param("sort", "oldest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("먼저쓴글"))
                .andExpect(jsonPath("$.content[1].title").value("나중쓴글"));
    }

    @Test
    void updatePost_성공() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        PostResponseDto created = createPost(songId, authorId, "원본제목", "원본내용");

        UpdatePostRequestDto request = new UpdatePostRequestDto("수정제목", "수정내용");

        mockMvc.perform(patch("/api/wiki-posts/{postId}", created.id())
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정제목"))
                .andExpect(jsonPath("$.content").value("수정내용"));
    }

    @Test
    void updatePost_작성자가_아니면_에러() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        PostResponseDto created = createPost(songId, authorId, "원본제목", "원본내용");

        UpdatePostRequestDto request = new UpdatePostRequestDto("수정제목", "수정내용");

        mockMvc.perform(patch("/api/wiki-posts/{postId}", created.id())
                        .header("X-User-Id", otherUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deletePost_작성자가_아니면_에러() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        PostResponseDto created = createPost(songId, authorId, "삭제될글", "내용");

        mockMvc.perform(delete("/api/wiki-posts/{postId}", created.id())
                        .header("X-User-Id", otherUserId.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deletePost_성공() throws Exception {
        UUID songId = createSongId();
        UUID authorId = UUID.randomUUID();
        PostResponseDto created = createPost(songId, authorId, "삭제될글", "내용");

        mockMvc.perform(delete("/api/wiki-posts/{postId}", created.id())
                        .header("X-User-Id", authorId.toString()))
                .andExpect(status().isNoContent());
    }

    private UUID createSongId() {
        Song song = songRepository.save(Song.create("track-" + UUID.randomUUID(), "곡명", "아티스트", "앨범", "http://img", 180000));
        return song.getId();
    }

    private PostResponseDto createPost(UUID songId, UUID authorId, String title, String content) throws Exception {
        CreatePostRequestDto request = new CreatePostRequestDto(songId, title, content);
        String response = mockMvc.perform(post("/api/wiki-posts")
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, PostResponseDto.class);
    }
}
