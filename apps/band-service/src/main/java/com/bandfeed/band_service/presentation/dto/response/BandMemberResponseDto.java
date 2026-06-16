package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.BandMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record BandMemberResponseDto(
        UUID bandId,
        UUID userId,
        String role,
        String status,
        LocalDateTime joinedAt
) {
    public static BandMemberResponseDto from(BandMember member) {
        return new BandMemberResponseDto(
                member.getBandId(),
                member.getUserId(),
                member.getRole().name(),
                member.getStatus().name(),
                member.getJoinedAt()
        );
    }
}
