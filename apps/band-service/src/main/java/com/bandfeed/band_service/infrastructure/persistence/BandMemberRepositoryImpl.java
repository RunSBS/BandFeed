package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
import com.bandfeed.band_service.infrastructure.entity.BandMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BandMemberRepositoryImpl implements BandMemberRepository {

    private final BandMemberJpaRepository jpa;

    @Override
    public BandMember save(BandMember member) {
        return jpa.save(BandMemberEntity.from(member)).toDomain();
    }

    @Override
    public Optional<BandMember> findByBandIdAndUserId(UUID bandId, UUID userId) {
        return jpa.findByBandIdAndUserId(bandId, userId).map(BandMemberEntity::toDomain);
    }

    @Override
    public List<BandMember> findAllByBandId(UUID bandId) {
        return jpa.findAllByBandId(bandId).stream().map(BandMemberEntity::toDomain).toList();
    }

    @Override
    public List<BandMember> findAllByUserId(UUID userId) {
        return jpa.findAllByUserId(userId).stream().map(BandMemberEntity::toDomain).toList();
    }

    @Override
    public void delete(BandMember member) {
        jpa.deleteById(member.getId());
    }
}
