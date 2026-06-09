package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.BandRole;
import com.bandfeed.band_service.domain.repository.BandMemberRepository;
import com.bandfeed.band_service.domain.repository.BandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // ── Band CRUD ─────────────────────────────────────────────────────────────

    @Override
    public Band createBand(CreateBandCommand command) {
        Band band = Band.create(command.name(), command.description(), command.leaderId());
        Band saved = bandRepository.save(band);
        bandMemberRepository.save(BandMember.create(saved.getId(), command.leaderId(), BandRole.LEADER));
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Band findBandById(UUID bandId) {
        return bandRepository.findById(bandId)
                .orElseThrow(() -> new RuntimeException("Band not found: " + bandId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Band> findAllBand(Pageable pageable) {
        return bandRepository.findAll(pageable);
    }

    @Override
    public Band updateBandInfo(UUID bandId, String name, String description, UUID requesterId) {
        Band band = findBandById(bandId);
        band.updateInfo(name, description);
        return bandRepository.save(band);
    }

    @Override
    public void deleteBand(UUID bandId, UUID requesterId) {
        Band band = findBandById(bandId);
        // TODO: 밴드 해체 시 wiki-service, chat-service의 연관 데이터 정리 필요
        // 추후 BandDisbandedEvent를 Kafka로 publish하여 이벤트 기반으로 처리 예정
        bandRepository.delete(band);
    }

    // ── BandMember CRUD ───────────────────────────────────────────────────────

    @Override
    public BandMember inviteBandMember(UUID bandId, UUID userId, UUID requesterId) {
        BandMember member = BandMember.create(bandId, userId, BandRole.MEMBER);
        return bandMemberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BandMember> findAllBandMember(UUID bandId) {
        return bandMemberRepository.findAllByBandId(bandId);
    }

    @Override
    public void removeBandMember(UUID bandId, UUID userId, UUID requesterId) {
        BandMember member = bandMemberRepository.findByBandIdAndUserId(bandId, userId)
                .orElseThrow(() -> new RuntimeException("BandMember not found"));
        bandMemberRepository.delete(member);
    }

    // ── 상태 변경 ──────────────────────────────────────────────────────────────

    @Override
    public Band transferBandLeader(UUID bandId, UUID newLeaderId, UUID requesterId) {
        Band band = findBandById(bandId);

        BandMember currentLeader = bandMemberRepository.findByBandIdAndUserId(bandId, requesterId)
                .orElseThrow(() -> new RuntimeException("BandMember not found: " + requesterId));
        currentLeader.demoteToMember();
        bandMemberRepository.save(currentLeader);

        BandMember newLeader = bandMemberRepository.findByBandIdAndUserId(bandId, newLeaderId)
                .orElseThrow(() -> new RuntimeException("BandMember not found: " + newLeaderId));
        newLeader.promoteToLeader();
        bandMemberRepository.save(newLeader);

        band.transferLeader(newLeaderId);
        return bandRepository.save(band);
    }
}
