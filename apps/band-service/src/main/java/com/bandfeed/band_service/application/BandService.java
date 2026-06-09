package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;

import java.util.List;

public interface BandService {

    Band create(CreateBandCommand command);
    Band findById(Long bandId);
    Band updateInfo(Long bandId, String name, String description, Long requesterId);
    void disband(Long bandId, Long requesterId);

    BandMember inviteMember(Long bandId, Long userId, Long requesterId);
    void kickOrLeave(Long bandId, Long userId, Long requesterId);
    List<BandMember> findMembers(Long bandId);
}
