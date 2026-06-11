package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.dto.request.CreateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateCommentRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.response.BandResponseDto;
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
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void findFeed_밴드멤버의_게시글만_조회() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        BandResponseDto myBand = createBand(userId, "내밴드");
        BandResponseDto otherBand = createBand(otherUserId, "남의밴드");

        createPost(myBand.id(), userId, "내밴드 글", "내용");
        createPost(otherBand.id(), otherUserId, "남의밴드 글", "내용");

        mockMvc.perform(get("/api/timeline-posts/feed")
                        .header("X-User-Id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("내밴드 글"));
    }

    @Test
    void findAllTimelinePostComment_성공() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        TimelinePostResponseDto post = createPost(bandId, authorId, "댓글목록글", "내용");

        CreateCommentRequestDto request = new CreateCommentRequestDto("댓글입니다");
        mockMvc.perform(post("/api/timeline-posts/{postId}/comments", post.id())
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/timeline-posts/{postId}/comments", post.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("댓글입니다"));
    }

    @Test
    void updateTimelinePostInfo_작성자가_아니면_에러() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        TimelinePostResponseDto created = createPost(bandId, authorId, "원본제목", "원본내용");

        UpdateTimelinePostRequestDto request = new UpdateTimelinePostRequestDto("수정제목", "수정내용");

        mockMvc.perform(patch("/api/timeline-posts/{postId}", created.id())
                        .header("X-User-Id", otherUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteTimelinePost_작성자가_아니면_에러() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        TimelinePostResponseDto created = createPost(bandId, authorId, "삭제될글", "내용");

        mockMvc.perform(delete("/api/timeline-posts/{postId}", created.id())
                        .header("X-User-Id", otherUserId.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteTimelinePostComment_작성자가_아니면_에러() throws Exception {
        UUID bandId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        TimelinePostResponseDto post = createPost(bandId, authorId, "댓글테스트글", "내용");

        CreateCommentRequestDto request = new CreateCommentRequestDto("댓글");
        String response = mockMvc.perform(post("/api/timeline-posts/{postId}/comments", post.id())
                        .header("X-User-Id", authorId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        CommentResponseDto comment = objectMapper.readValue(response, CommentResponseDto.class);

        mockMvc.perform(delete("/api/timeline-posts/{postId}/comments/{commentId}", post.id(), comment.id())
                        .header("X-User-Id", otherUserId.toString()))
                .andExpect(status().isForbidden());
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

    private BandResponseDto createBand(UUID leaderId, String name) throws Exception {
        CreateBandRequestDto request = new CreateBandRequestDto(name, "설명");
        String response = mockMvc.perform(post("/api/bands")
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, BandResponseDto.class);
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
