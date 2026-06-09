package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.infrastructure.entity.BandMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBandMemberRepository extends JpaRepository<BandMemberEntity, Long> {
    Optional<BandMemberEntity> findByBandIdAndUserId(Long bandId, Long userId);
    List<BandMemberEntity> findAllByBandId(Long bandId);
}
