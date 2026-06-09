package com.bandfeed.band_service.presentation.docs;

import org.springframework.http.ResponseEntity;

public interface BandControllerDocs {
    ResponseEntity<?> createBand(Object request);

    ResponseEntity<?> getBand(Long bandId);

    ResponseEntity<?> listBands(int page, int size);

    ResponseEntity<?> disbandBand(Long bandId);

    ResponseEntity<?> inviteMember(Long bandId, Object request);

    ResponseEntity<?> acceptInvite(Long bandId);

    ResponseEntity<?> leave(Long bandId);

    ResponseEntity<?> transferLeader(Long bandId, Object request);
}
