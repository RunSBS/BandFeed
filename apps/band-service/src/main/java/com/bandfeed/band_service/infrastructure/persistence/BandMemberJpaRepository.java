package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.BandMemberStatus;
import com.bandfeed.band_service.infrastructure.entity.BandMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandMemberJpaRepository extends JpaRepository<BandMemberEntity, UUID> {
    Optional<BandMemberEntity> findByBandIdAndUserId(UUID bandId, UUID userId);
    List<BandMemberEntity> findAllByBandId(UUID bandId);
    List<BandMemberEntity> findAllByBandIdAndStatus(UUID bandId, BandMemberStatus status);
    List<BandMemberEntity> findAllByUserIdAndStatus(UUID userId, BandMemberStatus status);
    List<BandMemberEntity> findAllByUserId(UUID userId);
}
