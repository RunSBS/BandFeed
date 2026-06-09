package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.repository.BandRepository;
import com.bandfeed.band_service.infrastructure.entity.BandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BandRepositoryImpl implements BandRepository {

    private final BandJpaRepository jpa;

    @Override
    public Band save(Band band) {
        return jpa.save(BandEntity.from(band)).toDomain();
    }

    @Override
    public Optional<Band> findById(UUID id) {
        return jpa.findById(id).map(BandEntity::toDomain);
    }

    @Override
    public Page<Band> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(BandEntity::toDomain);
    }

    @Override
    public void delete(Band band) {
        jpa.deleteById(band.getId());
    }
}
