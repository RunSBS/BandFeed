package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.repository.BandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BandRepositoryImpl implements BandRepository {

    private final JpaBandRepository jpa;

    @Override
    public Optional<Band> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Band save(Band band) {
        return jpa.save(band);
    }

    @Override
    public void delete(Band band) {
        jpa.delete(band);
    }
}
