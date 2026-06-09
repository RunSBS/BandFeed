package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.Band;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BandRepository {
    Band save(Band band);
    Optional<Band> findById(UUID id);
    Page<Band> findAll(Pageable pageable);
    void delete(Band band);
}
