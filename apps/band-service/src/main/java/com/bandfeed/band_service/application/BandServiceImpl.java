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
import java.util.UUID;

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
    public Band findById(UUID bandId) {
        return bandRepository.findById(bandId)
                .orElseThrow(() -> new RuntimeException("Band not found: " + bandId));
    }

    @Override
    public Band updateInfo(UUID bandId, String name, String description, UUID requesterId) {
        Band band = findById(bandId);
        band.updateInfo(name, description);
        return bandRepository.save(band);
    }

    @Override
    public void disband(UUID bandId, UUID requesterId) {
        Band band = findById(bandId);
        // TODO: 밴드 해체 시 wiki-service, chat-service의 연관 데이터 정리 필요
        // 추후 BandDisbandedEvent를 Kafka로 publish하여 이벤트 기반으로 처리 예정
        bandRepository.delete(band);
    }

    @Override
    public BandMember inviteMember(UUID bandId, UUID userId, UUID requesterId) {
        BandMember member = BandMember.create(bandId, userId, BandRole.MEMBER);
        return bandMemberRepository.save(member);
    }

    @Override
    public void kickOrLeave(UUID bandId, UUID userId, UUID requesterId) {
        BandMember member = bandMemberRepository.findByBandIdAndUserId(bandId, userId)
                .orElseThrow(() -> new RuntimeException("BandMember not found"));
        bandMemberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BandMember> findMembers(UUID bandId) {
        return bandMemberRepository.findAllByBandId(bandId);
    }
}
