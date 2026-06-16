package com.bandfeed.band_service.presentation.docs;

import com.bandfeed.band_service.presentation.dto.request.CreateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.InviteMemberRequestDto;
import com.bandfeed.band_service.presentation.dto.request.TransferLeaderRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateMemberStatusRequestDto;
import com.bandfeed.band_service.presentation.dto.response.BandMemberResponseDto;
import com.bandfeed.band_service.presentation.dto.response.BandResponseDto;
import common.dto.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface BandControllerDocs {

    @PostMapping
    ResponseEntity<CommonResponse<BandResponseDto>> createBand(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateBandRequestDto request);

    @GetMapping("/{bandId}")
    ResponseEntity<CommonResponse<BandResponseDto>> findBandById(@PathVariable UUID bandId);

    @GetMapping
    ResponseEntity<CommonResponse<List<BandResponseDto>>> findBands(
            @RequestParam(required = false) Boolean mine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId);

    @PatchMapping("/{bandId}")
    ResponseEntity<CommonResponse<BandResponseDto>> updateBandInfo(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateBandRequestDto request);

    @DeleteMapping("/{bandId}")
    ResponseEntity<CommonResponse<?>> disbandBand(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId);

    @PostMapping("/{bandId}/members")
    ResponseEntity<CommonResponse<BandMemberResponseDto>> inviteBandMember(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody InviteMemberRequestDto request);

    @GetMapping("/{bandId}/members")
    ResponseEntity<CommonResponse<List<BandMemberResponseDto>>> findAllBandMember(@PathVariable UUID bandId);

    @GetMapping("/invitations")
    ResponseEntity<CommonResponse<List<BandMemberResponseDto>>> findMyPendingInvitations(
            @RequestHeader("X-User-Id") UUID userId);

    @PatchMapping("/{bandId}/members/me")
    ResponseEntity<CommonResponse<BandMemberResponseDto>> acceptInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateMemberStatusRequestDto request);

    @DeleteMapping("/{bandId}/members/me")
    ResponseEntity<CommonResponse<?>> declineInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId);

    @DeleteMapping("/{bandId}/members/{targetUserId}")
    ResponseEntity<CommonResponse<?>> removeBandMember(
            @PathVariable UUID bandId,
            @PathVariable UUID targetUserId,
            @RequestHeader("X-User-Id") UUID userId);

    @PatchMapping("/{bandId}/leader")
    ResponseEntity<CommonResponse<BandResponseDto>> transferBandLeader(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody TransferLeaderRequestDto request);
}
