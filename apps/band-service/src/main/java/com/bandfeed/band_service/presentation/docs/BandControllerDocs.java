package com.bandfeed.band_service.presentation.docs;

import com.bandfeed.band_service.presentation.dto.request.CreateBandRequestDto;
import com.bandfeed.band_service.presentation.dto.request.InviteMemberRequestDto;
import com.bandfeed.band_service.presentation.dto.request.TransferLeaderRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateBandRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface BandControllerDocs {

    // ── Band CRUD ─────────────────────────────────────────────────────────────

    @PostMapping
    ResponseEntity<?> createBand(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateBandRequestDto request);

    @GetMapping("/{bandId}")
    ResponseEntity<?> findBandById(@PathVariable UUID bandId);

    @GetMapping
    ResponseEntity<?> findAllBand(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size);

    @PatchMapping("/{bandId}")
    ResponseEntity<?> updateBandInfo(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateBandRequestDto request);

    @DeleteMapping("/{bandId}")
    ResponseEntity<?> disbandBand(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId);

    // ── BandMember CRUD ───────────────────────────────────────────────────────

    @PostMapping("/{bandId}/members")
    ResponseEntity<?> inviteBandMember(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody InviteMemberRequestDto request);

    @GetMapping("/{bandId}/members")
    ResponseEntity<?> findAllBandMember(@PathVariable UUID bandId);

    @GetMapping("/my")
    ResponseEntity<?> findMyBands(@RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/invitations/me")
    ResponseEntity<?> findMyPendingInvitations(@RequestHeader("X-User-Id") UUID userId);

    @PostMapping("/{bandId}/members/accept")
    ResponseEntity<?> acceptInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId);

    @DeleteMapping("/{bandId}/members/decline")
    ResponseEntity<?> declineInvitation(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId);

    @DeleteMapping("/{bandId}/members/{targetUserId}")
    ResponseEntity<?> removeBandMember(
            @PathVariable UUID bandId,
            @PathVariable UUID targetUserId,
            @RequestHeader("X-User-Id") UUID userId);

    // ── 상태 변경 ──────────────────────────────────────────────────────────────

    @PatchMapping("/{bandId}/leader")
    ResponseEntity<?> transferBandLeader(
            @PathVariable UUID bandId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody TransferLeaderRequestDto request);
}
