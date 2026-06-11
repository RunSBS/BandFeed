package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.presentation.dto.request.ChangePasswordRequestDto;
import com.bandfeed.user_service.presentation.dto.request.LoginRequestDto;
import com.bandfeed.user_service.presentation.dto.request.SignupRequestDto;
import com.bandfeed.user_service.presentation.dto.request.UpdateProfileRequestDto;
import com.bandfeed.user_service.presentation.dto.response.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup_성공() throws Exception {
        SignupRequestDto request = new SignupRequestDto("user1@bandfeed.com", "password1234", "닉네임1");

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("user1@bandfeed.com"))
                .andExpect(jsonPath("$.nickname").value("닉네임1"));
    }

    @Test
    void signup_이메일_중복이면_실패() throws Exception {
        SignupRequestDto request = new SignupRequestDto("dup@bandfeed.com", "password1234", "닉네임");
        signup(request);

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("U002"));
    }

    @Test
    void login_성공() throws Exception {
        SignupRequestDto signupRequest = new SignupRequestDto("login@bandfeed.com", "password1234", "로그인유저");
        signup(signupRequest);

        LoginRequestDto loginRequest = new LoginRequestDto("login@bandfeed.com", "password1234");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void login_비밀번호_틀리면_실패() throws Exception {
        SignupRequestDto signupRequest = new SignupRequestDto("login2@bandfeed.com", "password1234", "로그인유저2");
        signup(signupRequest);

        LoginRequestDto loginRequest = new LoginRequestDto("login2@bandfeed.com", "wrongpassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("U003"));
    }

    @Test
    void findUserById_성공() throws Exception {
        UserResponseDto created = signup(new SignupRequestDto("find@bandfeed.com", "password1234", "조회유저"));

        mockMvc.perform(get("/api/users/{userId}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.id().toString()))
                .andExpect(jsonPath("$.nickname").value("조회유저"));
    }

    @Test
    void findUserById_존재하지_않으면_실패() throws Exception {
        mockMvc.perform(get("/api/users/{userId}", java.util.UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("U001"));
    }

    @Test
    void updateProfile_성공() throws Exception {
        UserResponseDto created = signup(new SignupRequestDto("update@bandfeed.com", "password1234", "수정전닉네임"));

        UpdateProfileRequestDto request = new UpdateProfileRequestDto("수정후닉네임", "https://image.url", "소개글");

        mockMvc.perform(patch("/api/users/me")
                        .header("X-User-Id", created.id().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("수정후닉네임"))
                .andExpect(jsonPath("$.introduction").value("소개글"));
    }

    @Test
    void changePassword_성공() throws Exception {
        UserResponseDto created = signup(new SignupRequestDto("pwchange@bandfeed.com", "password1234", "비번변경유저"));

        ChangePasswordRequestDto request = new ChangePasswordRequestDto("password1234", "newpassword5678");

        mockMvc.perform(patch("/api/users/me/password")
                        .header("X-User-Id", created.id().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        LoginRequestDto loginRequest = new LoginRequestDto("pwchange@bandfeed.com", "newpassword5678");
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void withdraw_성공() throws Exception {
        UserResponseDto created = signup(new SignupRequestDto("withdraw@bandfeed.com", "password1234", "탈퇴유저"));

        mockMvc.perform(delete("/api/users/me")
                        .header("X-User-Id", created.id().toString()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{userId}", created.id()))
                .andExpect(status().isNotFound());
    }

    private UserResponseDto signup(SignupRequestDto request) throws Exception {
        String response = mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, UserResponseDto.class);
    }
}
