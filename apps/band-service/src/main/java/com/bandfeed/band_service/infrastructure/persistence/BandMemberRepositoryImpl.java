package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
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
        return jpa.findByBandIdAndUserId(bandId, userId);
    }

    @Override
    public List<BandMember> findAllByBandId(Long bandId) {
        return jpa.findAllByBandId(bandId);
    }

    @Override
    public BandMember save(BandMember member) {
        return jpa.save(member);
    }

    @Override
    public void delete(BandMember member) {
        jpa.delete(member);
    }
}
