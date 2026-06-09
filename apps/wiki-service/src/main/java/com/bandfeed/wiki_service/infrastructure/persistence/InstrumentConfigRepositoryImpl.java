package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.repository.InstrumentConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InstrumentConfigRepositoryImpl implements InstrumentConfigRepository {

    private final JpaInstrumentConfigRepository jpaInstrumentConfigRepository;

    @Override
    public List<InstrumentConfig> findAllBySongId(Long songId) {
        return jpaInstrumentConfigRepository.findAllBySongId(songId);
    }

    @Override
    public InstrumentConfig save(InstrumentConfig config) {
        return jpaInstrumentConfigRepository.save(config);
    }

    @Override
    public void delete(InstrumentConfig config) {
        jpaInstrumentConfigRepository.delete(config);
    }
}
