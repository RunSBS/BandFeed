package com.bandfeed.band_service.presentation.dto.request;

import java.util.UUID;

public record TransferLeaderRequestDto(
        UUID newLeaderId
) {}
