package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.repository.InstrumentConfigRepository;
import com.bandfeed.wiki_service.infrastructure.entity.InstrumentConfigEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InstrumentConfigRepositoryImpl implements InstrumentConfigRepository {

    private final InstrumentConfigJpaRepository jpa;

    @Override
    public Optional<InstrumentConfig> findById(UUID id) {
        return jpa.findById(id).map(InstrumentConfigEntity::toDomain);
    }

    @Override
    public List<InstrumentConfig> findAllByPostId(UUID postId) {
        return jpa.findAllByPostId(postId).stream().map(InstrumentConfigEntity::toDomain).toList();
    }

    @Override
    public InstrumentConfig save(InstrumentConfig config) {
        return jpa.save(InstrumentConfigEntity.from(config)).toDomain();
    }

    @Override
    public void delete(InstrumentConfig config) {
        jpa.deleteById(config.getId());
    }
}
