package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.BandMember;

import java.util.List;
import java.util.Optional;

public interface BandMemberRepository {
    Optional<BandMember> findByBandIdAndUserId(Long bandId, Long userId);
    List<BandMember> findAllByBandId(Long bandId);
    BandMember save(BandMember member);
    void delete(BandMember member);
}
