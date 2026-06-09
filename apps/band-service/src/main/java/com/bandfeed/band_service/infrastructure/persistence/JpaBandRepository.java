package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBandRepository extends JpaRepository<Band, Long> {
}
