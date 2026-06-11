package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.dto.request.CreateCommentRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.response.CommentResponseDto;
import com.bandfeed.band_service.presentation.dto.response.TimelinePostResponseDto;
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
class TimelinePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAndFindTimelinePost_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        TimelinePostResponseDto created = createPost(bandId, authorId, "합주 후기", "오늘 합주 좋았다");

        mockMvc.perform(get("/api/timeline-posts/{postId}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("합주 후기"))
                .andExpect(jsonPath("$.content").value("오늘 합주 좋았다"))
                .andExpect(jsonPath("$.bandId").value(bandId.toString()));
    }

    @Test
    void findAllTimelinePost_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        createPost(bandId, authorId, "글1", "내용1");
        createPost(bandId, authorId, "글2", "내용2");

        mockMvc.perform(get("/api/timeline-posts").param("bandId", bandId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateTimelinePostInfo_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        TimelinePostResponseDto created = createPost(bandId, authorId, "원본제목", "원본내용");

        UpdateTimelinePostRequestDto request = new UpdateTimelinePostRequestDto("수정제목", "수정내용");

        mockMvc.perform(patch("/api/timeline-posts/{postId}", created.id())
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정제목"))
                .andExpect(jsonPath("$.content").value("수정내용"));
    }

    @Test
    void deleteTimelinePost_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        TimelinePostResponseDto created = createPost(bandId, authorId, "삭제될글", "내용");

        mockMvc.perform(delete("/api/timeline-posts/{postId}", created.id())
                        .header("X-User-Id", authorId.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void createAndDeleteComment_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        TimelinePostResponseDto post = createPost(bandId, authorId, "댓글테스트글", "내용");

        CreateCommentRequestDto request = new CreateCommentRequestDto("좋은 글이네요");

        String response = mockMvc.perform(post("/api/timeline-posts/{postId}/comments", post.id())
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("좋은 글이네요"))
                .andExpect(jsonPath("$.postId").value(post.id().toString()))
                .andReturn().getResponse().getContentAsString();

        CommentResponseDto comment = objectMapper.readValue(response, CommentResponseDto.class);

        mockMvc.perform(delete("/api/timeline-posts/{postId}/comments/{commentId}", post.id(), comment.id())
                        .header("X-User-Id", authorId.toString()))
                .andExpect(status().isNoContent());
    }

    private TimelinePostResponseDto createPost(UUID bandId, UUID authorId, String title, String content) throws Exception {
        CreateTimelinePostRequestDto request = new CreateTimelinePostRequestDto(bandId, title, content);
        String response = mockMvc.perform(post("/api/timeline-posts")
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, TimelinePostResponseDto.class);
    }
}
