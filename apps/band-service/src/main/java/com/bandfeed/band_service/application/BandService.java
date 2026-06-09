package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;

import java.util.List;
import java.util.UUID;

public interface BandService {

    Band create(CreateBandCommand command);
    Band findById(UUID bandId);
    Band updateInfo(UUID bandId, String name, String description, UUID requesterId);
    void disband(UUID bandId, UUID requesterId);

    BandMember inviteMember(UUID bandId, UUID userId, UUID requesterId);
    void kickOrLeave(UUID bandId, UUID userId, UUID requesterId);
    List<BandMember> findMembers(UUID bandId);
}
