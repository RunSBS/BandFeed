package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.BandMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record BandMemberResponseDto(
        UUID userId,
        String role,
        LocalDateTime joinedAt
) {
    public static BandMemberResponseDto from(BandMember member) {
        return new BandMemberResponseDto(
                member.getUserId(),
                member.getRole().name(),
                member.getJoinedAt()
        );
    }
}
