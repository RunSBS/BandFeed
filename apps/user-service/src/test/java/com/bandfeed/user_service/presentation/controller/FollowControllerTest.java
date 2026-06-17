package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.presentation.dto.request.FollowRequestDto;
import com.bandfeed.user_service.presentation.dto.request.SignupRequestDto;
import com.bandfeed.user_service.presentation.dto.response.UserResponseDto;
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
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void follow_성공() throws Exception {
        UserResponseDto follower = signup("follower1@bandfeed.com", "팔로워1");
        UserResponseDto followee = signup("followee1@bandfeed.com", "팔로위1");

        FollowRequestDto request = new FollowRequestDto(followee.id());

        mockMvc.perform(post("/api/follows")
                        .header("X-User-Id", follower.id().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.followerId").value(follower.id().toString()))
                .andExpect(jsonPath("$.data.followeeId").value(followee.id().toString()));
    }

    @Test
    void follow_자기자신_팔로우_불가() throws Exception {
        UserResponseDto user = signup("self@bandfeed.com", "셀프유저");

        FollowRequestDto request = new FollowRequestDto(user.id());

        mockMvc.perform(post("/api/follows")
                        .header("X-User-Id", user.id().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("U006"));
    }

    @Test
    void follow_중복_팔로우_불가() throws Exception {
        UserResponseDto follower = signup("follower2@bandfeed.com", "팔로워2");
        UserResponseDto followee = signup("followee2@bandfeed.com", "팔로위2");

        follow(follower.id(), followee.id());

        FollowRequestDto request = new FollowRequestDto(followee.id());
        mockMvc.perform(post("/api/follows")
                        .header("X-User-Id", follower.id().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("U004"));
    }

    @Test
    void unfollow_성공() throws Exception {
        UserResponseDto follower = signup("follower3@bandfeed.com", "팔로워3");
        UserResponseDto followee = signup("followee3@bandfeed.com", "팔로위3");

        follow(follower.id(), followee.id());

        mockMvc.perform(delete("/api/follows/{followeeId}", followee.id())
                        .header("X-User-Id", follower.id().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void unfollow_존재하지_않으면_실패() throws Exception {
        UserResponseDto follower = signup("follower4@bandfeed.com", "팔로워4");
        UserResponseDto followee = signup("followee4@bandfeed.com", "팔로위4");

        mockMvc.perform(delete("/api/follows/{followeeId}", followee.id())
                        .header("X-User-Id", follower.id().toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("U005"));
    }

    @Test
    void findFollowersAndFollowings_성공() throws Exception {
        UserResponseDto follower = signup("follower5@bandfeed.com", "팔로워5");
        UserResponseDto followee = signup("followee5@bandfeed.com", "팔로위5");

        follow(follower.id(), followee.id());

        mockMvc.perform(get("/api/follows/{userId}/followers", followee.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].followerId").value(follower.id().toString()));

        mockMvc.perform(get("/api/follows/{userId}/followings", follower.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].followeeId").value(followee.id().toString()));
    }

    private UserResponseDto signup(String email, String nickname) throws Exception {
        SignupRequestDto request = new SignupRequestDto(email, "password1234", nickname);
        String response = mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(objectMapper.readTree(response).get("data").toString(), UserResponseDto.class);
    }

    private void follow(UUID followerId, UUID followeeId) throws Exception {
        FollowRequestDto request = new FollowRequestDto(followeeId);
        mockMvc.perform(post("/api/follows")
                        .header("X-User-Id", followerId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
