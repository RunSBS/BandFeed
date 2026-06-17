package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.BandMember;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandMemberRepository {
    BandMember save(BandMember member);
    Optional<BandMember> findByBandIdAndUserId(UUID bandId, UUID userId);
    List<BandMember> findAllByBandId(UUID bandId);
    List<BandMember> findAllActiveByBandId(UUID bandId);
    List<BandMember> findAllPendingByUserId(UUID userId);
    List<BandMember> findAllByUserId(UUID userId);
    void delete(BandMember member);
}
