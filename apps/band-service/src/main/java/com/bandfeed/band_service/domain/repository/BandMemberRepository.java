package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.BandMember;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandMemberRepository {
    BandMember save(BandMember member);
    Optional<BandMember> findByBandIdAndUserId(UUID bandId, UUID userId);
    List<BandMember> findAllByBandId(UUID bandId);
    void delete(BandMember member);
}
