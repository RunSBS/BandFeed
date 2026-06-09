package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.BandRole;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
import com.bandfeed.band_service.domain.repository.BandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;
    private final BandMemberRepository bandMemberRepository;

    @Override
    public Band create(CreateBandCommand command) {
        Band band = Band.create(command.name(), command.description(), command.leaderId());
        Band saved = bandRepository.save(band);
        bandMemberRepository.save(BandMember.create(saved.getId(), command.leaderId(), BandRole.LEADER));
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Band findById(Long bandId) {
        return bandRepository.findById(bandId)
                .orElseThrow(() -> new RuntimeException("Band not found: " + bandId));
    }

    @Override
    public Band updateInfo(Long bandId, String name, String description, Long requesterId) {
        Band band = findById(bandId);
        band.updateInfo(name, description);
        return bandRepository.save(band);
    }

    @Override
    public void disband(Long bandId, Long requesterId) {
        Band band = findById(bandId);
        band.disband();
        bandRepository.save(band);
    }

    @Override
    public BandMember inviteMember(Long bandId, Long userId, Long requesterId) {
        BandMember member = BandMember.create(bandId, userId, BandRole.MEMBER);
        return bandMemberRepository.save(member);
    }

    @Override
    public void kickOrLeave(Long bandId, Long userId, Long requesterId) {
        BandMember member = bandMemberRepository.findByBandIdAndUserId(bandId, userId)
                .orElseThrow(() -> new RuntimeException("BandMember not found"));
        bandMemberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BandMember> findMembers(Long bandId) {
        return bandMemberRepository.findAllByBandId(bandId);
    }
}
