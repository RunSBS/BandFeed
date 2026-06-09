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
import com.bandfeed.band_service.presentation.dto.response.BandMemberResponseDto;
import com.bandfeed.band_service.presentation.dto.response.BandResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bands")
@RequiredArgsConstructor
public class BandController implements BandControllerDocs {

    private final BandService bandService;

    // ── Band CRUD ─────────────────────────────────────────────────────────────

    @Override
    @PostMapping
    public ResponseEntity<?> createBand(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateBandRequestDto request) {
        Band band = bandService.createBand(new CreateBandCommand(request.name(), request.description(), userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(BandResponseDto.from(band));
    }

    @Override
    @GetMapping("/{bandId}")
    public ResponseEntity<?> findBandById(@PathVariable UUID bandId) {
        return ResponseEntity.ok(BandResponseDto.from(bandService.findBandById(bandId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> findAllBand(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(bandService.findAllBand(PageRequest.of(page, size)).map(BandResponseDto::from));
    }

    @Override
    @PatchMapping("/{bandId}")
    public ResponseEntity<?> updateBandInfo(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateBandRequestDto request) {
        Band band = bandService.updateBandInfo(bandId, request.name(), request.description(), userId);
        return ResponseEntity.ok(BandResponseDto.from(band));
    }

    @Override
    @DeleteMapping("/{bandId}")
    public ResponseEntity<?> disbandBand(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId) {
        bandService.deleteBand(bandId, userId);
        return ResponseEntity.noContent().build();
    }

    // ── BandMember CRUD ───────────────────────────────────────────────────────

    @Override
    @PostMapping("/{bandId}/members")
    public ResponseEntity<?> inviteBandMember(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody InviteMemberRequestDto request) {
        BandMember member = bandService.inviteBandMember(bandId, request.inviteeId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(BandMemberResponseDto.from(member));
    }

    @Override
    @GetMapping("/{bandId}/members")
    public ResponseEntity<?> findAllBandMember(@PathVariable UUID bandId) {
        List<BandMember> members = bandService.findAllBandMember(bandId);
        return ResponseEntity.ok(members.stream().map(BandMemberResponseDto::from).toList());
    }

    @Override
    @DeleteMapping("/{bandId}/members/{targetUserId}")
    public ResponseEntity<?> removeBandMember(
            @PathVariable UUID bandId,
            @PathVariable UUID targetUserId,
            @RequestHeader("X-User-Id") UUID userId) {
        bandService.removeBandMember(bandId, targetUserId, userId);
        return ResponseEntity.noContent().build();
    }

    // ── 상태 변경 ──────────────────────────────────────────────────────────────

    @Override
    @PatchMapping("/{bandId}/leader")
    public ResponseEntity<?> transferBandLeader(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody TransferLeaderRequestDto request) {
        Band band = bandService.transferBandLeader(bandId, request.newLeaderId(), userId);
        return ResponseEntity.ok(BandResponseDto.from(band));
    }
}
