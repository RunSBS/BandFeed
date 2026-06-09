package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.Band;

import java.util.Optional;
import java.util.UUID;

public interface BandRepository {
    Optional<Band> findById(UUID id);
    Band save(Band band);
    void delete(Band band);
}
