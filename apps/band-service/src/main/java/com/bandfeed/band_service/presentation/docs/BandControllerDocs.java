package com.bandfeed.band_service.presentation.docs;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BandControllerDocs {
    ResponseEntity<?> createBand(Object request);

    ResponseEntity<?> getBand(UUID bandId);

    ResponseEntity<?> listBands(int page, int size);

    ResponseEntity<?> disbandBand(UUID bandId);

    ResponseEntity<?> inviteMember(UUID bandId, Object request);

    ResponseEntity<?> acceptInvite(UUID bandId);

    ResponseEntity<?> leave(UUID bandId);

    ResponseEntity<?> transferLeader(UUID bandId, Object request);
}
