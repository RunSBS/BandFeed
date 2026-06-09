package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
import com.bandfeed.band_service.infrastructure.entity.BandMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BandMemberRepositoryImpl implements BandMemberRepository {

    private final JpaBandMemberRepository jpa;

    @Override
    public Optional<BandMember> findByBandIdAndUserId(Long bandId, Long userId) {
        return jpa.findByBandIdAndUserId(bandId, userId).map(BandMemberEntity::toDomain);
    }

    @Override
    public List<BandMember> findAllByBandId(Long bandId) {
        return jpa.findAllByBandId(bandId).stream().map(BandMemberEntity::toDomain).toList();
    }

    @Override
    public BandMember save(BandMember member) {
        return jpa.save(BandMemberEntity.from(member)).toDomain();
    }

    @Override
    public void delete(BandMember member) {
        jpa.deleteById(member.getId());
    }
}
