package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.BandMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBandMemberRepository extends JpaRepository<BandMember, Long> {
    Optional<BandMember> findByBandIdAndUserId(Long bandId, Long userId);
    List<BandMember> findAllByBandId(Long bandId);
}
