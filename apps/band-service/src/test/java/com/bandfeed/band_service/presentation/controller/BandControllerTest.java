package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.dto.request.CreateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.InviteMemberRequestDto;
import com.bandfeed.band_service.presentation.dto.request.TransferLeaderRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.response.BandResponseDto;
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
class BandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBand_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        CreateBandRequestDto request = new CreateBandRequestDto("우리밴드", "합주 모임");

        mockMvc.perform(post("/api/bands")
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("우리밴드"))
                .andExpect(jsonPath("$.description").value("합주 모임"))
                .andExpect(jsonPath("$.leaderId").value(leaderId.toString()));
    }

    @Test
    void findBandById_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "조회용밴드", "설명");

        mockMvc.perform(get("/api/bands/{bandId}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.id().toString()))
                .andExpect(jsonPath("$.name").value("조회용밴드"));
    }

    @Test
    void findBandById_존재하지_않으면_에러() throws Exception {
        mockMvc.perform(get("/api/bands/{bandId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllBand_성공() throws Exception {
        createBand(UUID.randomUUID(), "밴드A", "설명A");
        createBand(UUID.randomUUID(), "밴드B", "설명B");

        mockMvc.perform(get("/api/bands").param("page", "0").param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateBandInfo_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "수정전", "설명");

        UpdateBandRequestDto request = new UpdateBandRequestDto("수정후", "수정된 설명");

        mockMvc.perform(patch("/api/bands/{bandId}", created.id())
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("수정후"))
                .andExpect(jsonPath("$.description").value("수정된 설명"));
    }

    @Test
    void updateBandInfo_리더가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "수정전", "설명");

        UpdateBandRequestDto request = new UpdateBandRequestDto("수정후", "수정된 설명");

        mockMvc.perform(patch("/api/bands/{bandId}", created.id())
                        .header("X-User-Id", otherUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void disbandBand_리더가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "해체될밴드", "설명");

        mockMvc.perform(delete("/api/bands/{bandId}", created.id())
                        .header("X-User-Id", otherUserId.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void inviteBandMember_리더가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        UUID inviteeId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "멤버초대밴드", "설명");

        InviteMemberRequestDto request = new InviteMemberRequestDto(inviteeId);

        mockMvc.perform(post("/api/bands/{bandId}/members", created.id())
                        .header("X-User-Id", otherUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void inviteBandMember_이미_멤버면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID inviteeId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "중복초대밴드", "설명");
        inviteMember(created.id(), leaderId, inviteeId);

        InviteMemberRequestDto request = new InviteMemberRequestDto(inviteeId);

        mockMvc.perform(post("/api/bands/{bandId}/members", created.id())
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void removeBandMember_리더가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        UUID inviteeId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "멤버제거밴드", "설명");
        inviteMember(created.id(), leaderId, inviteeId);

        mockMvc.perform(delete("/api/bands/{bandId}/members/{userId}", created.id(), inviteeId)
                        .header("X-User-Id", otherUserId.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void transferBandLeader_리더가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        UUID newLeaderId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "리더위임밴드", "설명");
        inviteMember(created.id(), leaderId, newLeaderId);

        TransferLeaderRequestDto request = new TransferLeaderRequestDto(newLeaderId);

        mockMvc.perform(patch("/api/bands/{bandId}/leader", created.id())
                        .header("X-User-Id", otherUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void transferBandLeader_새리더가_멤버가_아니면_에러() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID notMemberId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "리더위임밴드", "설명");

        TransferLeaderRequestDto request = new TransferLeaderRequestDto(notMemberId);

        mockMvc.perform(patch("/api/bands/{bandId}/leader", created.id())
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void inviteAndFindBandMembers_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID inviteeId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "멤버초대밴드", "설명");

        InviteMemberRequestDto request = new InviteMemberRequestDto(inviteeId);

        mockMvc.perform(post("/api/bands/{bandId}/members", created.id())
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(inviteeId.toString()))
                .andExpect(jsonPath("$.role").value("MEMBER"));

        mockMvc.perform(get("/api/bands/{bandId}/members", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void removeBandMember_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID inviteeId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "멤버제거밴드", "설명");
        inviteMember(created.id(), leaderId, inviteeId);

        mockMvc.perform(delete("/api/bands/{bandId}/members/{userId}", created.id(), inviteeId)
                        .header("X-User-Id", leaderId.toString()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/bands/{bandId}/members", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void transferBandLeader_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        UUID newLeaderId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "리더위임밴드", "설명");
        inviteMember(created.id(), leaderId, newLeaderId);

        TransferLeaderRequestDto request = new TransferLeaderRequestDto(newLeaderId);

        mockMvc.perform(patch("/api/bands/{bandId}/leader", created.id())
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leaderId").value(newLeaderId.toString()));
    }

    @Test
    void disbandBand_성공() throws Exception {
        UUID leaderId = UUID.randomUUID();
        BandResponseDto created = createBand(leaderId, "해체될밴드", "설명");

        mockMvc.perform(delete("/api/bands/{bandId}", created.id())
                        .header("X-User-Id", leaderId.toString()))
                .andExpect(status().isNoContent());
    }

    private BandResponseDto createBand(UUID leaderId, String name, String description) throws Exception {
        CreateBandRequestDto request = new CreateBandRequestDto(name, description);
        String response = mockMvc.perform(post("/api/bands")
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, BandResponseDto.class);
    }

    private void inviteMember(UUID bandId, UUID leaderId, UUID inviteeId) throws Exception {
        InviteMemberRequestDto request = new InviteMemberRequestDto(inviteeId);
        mockMvc.perform(post("/api/bands/{bandId}/members", bandId)
                        .header("X-User-Id", leaderId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
