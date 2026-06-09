package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.infrastructure.entity.BandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandJpaRepository extends JpaRepository<BandEntity, Long> {
}
