package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.Band;

import java.util.Optional;

public interface BandRepository {
    Optional<Band> findById(Long id);
    Band save(Band band);
    void delete(Band band);
}
