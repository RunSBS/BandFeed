package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BandService {

    // ── Band CRUD ─────────────────────────────────────────────────────────────
    Band createBand(CreateBandCommand command);
    Band findBandById(UUID bandId);
    Page<Band> findAllBand(Pageable pageable);
    Band updateBandInfo(UUID bandId, String name, String description, UUID requesterId);
    void deleteBand(UUID bandId, UUID requesterId);

    // ── BandMember CRUD ───────────────────────────────────────────────────────
    BandMember inviteBandMember(UUID bandId, UUID inviteeId, UUID requesterId);
    List<BandMember> findAllBandMember(UUID bandId);
    List<BandMember> findMyPendingInvitations(UUID userId);
    BandMember acceptInvitation(UUID bandId, UUID userId);
    void declineInvitation(UUID bandId, UUID userId);
    void removeBandMember(UUID bandId, UUID userId, UUID requesterId);

    // ── 상태 변경 ──────────────────────────────────────────────────────────────
    Band transferBandLeader(UUID bandId, UUID newLeaderId, UUID requesterId);
}
