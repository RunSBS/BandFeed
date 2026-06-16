package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.application.BandService;
import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.presentation.docs.BandControllerDocs;
import com.bandfeed.band_service.presentation.dto.request.CreateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.InviteMemberRequestDto;
import com.bandfeed.band_service.presentation.dto.request.TransferLeaderRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateMemberStatusRequestDto;
import com.bandfeed.band_service.presentation.dto.response.BandMemberResponseDto;
import com.bandfeed.band_service.presentation.dto.response.BandResponseDto;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bands")
@RequiredArgsConstructor
public class BandController implements BandControllerDocs {

    private final BandService bandService;

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<BandResponseDto>> createBand(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateBandRequestDto request) {
        Band band = bandService.createBand(new CreateBandCommand(request.name(), request.description(), userId));
        return CommonResponse.created("밴드가 생성되었습니다.", BandResponseDto.from(band));
    }

    @Override
    @GetMapping("/{bandId}")
    public ResponseEntity<CommonResponse<BandResponseDto>> findBandById(@PathVariable UUID bandId) {
        return CommonResponse.ok(BandResponseDto.from(bandService.findBandById(bandId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<List<BandResponseDto>>> findBands(
            @RequestParam(required = false) Boolean mine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId) {
        if (Boolean.TRUE.equals(mine)) {
            List<BandResponseDto> myBands = bandService.findMyBands(userId).stream()
                    .map(BandResponseDto::from)
                    .toList();
            return CommonResponse.ok(myBands);
        }
        List<BandResponseDto> bands = bandService.findAllBand(PageRequest.of(page, size))
                .map(BandResponseDto::from)
                .getContent();
        return CommonResponse.ok(bands);
    }

    @Override
    @PatchMapping("/{bandId}")
    public ResponseEntity<CommonResponse<BandResponseDto>> updateBandInfo(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateBandRequestDto request) {
        Band band = bandService.updateBandInfo(bandId, request.name(), request.description(), userId);
        return CommonResponse.ok(BandResponseDto.from(band));
    }

    @Override
    @DeleteMapping("/{bandId}")
    public ResponseEntity<CommonResponse<?>> disbandBand(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId) {
        bandService.deleteBand(bandId, userId);
        return CommonResponse.ok("밴드가 해산되었습니다.");
    }

    @Override
    @PostMapping("/{bandId}/members")
    public ResponseEntity<CommonResponse<BandMemberResponseDto>> inviteBandMember(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody InviteMemberRequestDto request) {
        BandMember member = bandService.inviteBandMember(bandId, request.inviteeId(), userId);
        return CommonResponse.created("초대를 보냈습니다.", BandMemberResponseDto.from(member));
    }

    @Override
    @GetMapping("/{bandId}/members")
    public ResponseEntity<CommonResponse<List<BandMemberResponseDto>>> findAllBandMember(@PathVariable UUID bandId) {
        List<BandMember> members = bandService.findAllBandMember(bandId);
        return CommonResponse.ok(members.stream().map(BandMemberResponseDto::from).toList());
    }

    @Override
    @GetMapping("/invitations")
    public ResponseEntity<CommonResponse<List<BandMemberResponseDto>>> findMyPendingInvitations(
            @RequestHeader("X-User-Id") UUID userId) {
        List<BandMember> invitations = bandService.findMyPendingInvitations(userId);
        return CommonResponse.ok(invitations.stream().map(BandMemberResponseDto::from).toList());
    }

    @Override
    @PatchMapping("/{bandId}/members/me")
    public ResponseEntity<CommonResponse<BandMemberResponseDto>> acceptInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateMemberStatusRequestDto request) {
        BandMember member = bandService.acceptInvitation(bandId, userId);
        return CommonResponse.ok(BandMemberResponseDto.from(member));
    }

    @Override
    @DeleteMapping("/{bandId}/members/me")
    public ResponseEntity<CommonResponse<?>> declineInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId) {
        bandService.declineInvitation(bandId, userId);
        return CommonResponse.ok("초대를 거절했습니다.");
    }

    @Override
    @DeleteMapping("/{bandId}/members/{targetUserId}")
    public ResponseEntity<CommonResponse<?>> removeBandMember(
            @PathVariable UUID bandId,
            @PathVariable UUID targetUserId,
            @RequestHeader("X-User-Id") UUID userId) {
        bandService.removeBandMember(bandId, targetUserId, userId);
        return CommonResponse.ok("멤버를 내보냈습니다.");
    }

    @Override
    @PatchMapping("/{bandId}/leader")
    public ResponseEntity<CommonResponse<BandResponseDto>> transferBandLeader(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody TransferLeaderRequestDto request) {
        Band band = bandService.transferBandLeader(bandId, request.newLeaderId(), userId);
        return CommonResponse.ok(BandResponseDto.from(band));
    }
}
