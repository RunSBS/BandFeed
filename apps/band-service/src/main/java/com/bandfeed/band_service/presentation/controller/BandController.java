package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.presentation.docs.BandControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bands")
@RequiredArgsConstructor
public class BandController implements BandControllerDocs {

    @Override
    public ResponseEntity<?> createBand(Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> getBand(Long bandId) {
        return null;
    }

    @Override
    public ResponseEntity<?> listBands(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> disbandBand(Long bandId) {
        return null;
    }

    @Override
    public ResponseEntity<?> inviteMember(Long bandId, Object request) {
        return null;
    }

    @Override
    public ResponseEntity<?> acceptInvite(Long bandId) {
        return null;
    }

    @Override
    public ResponseEntity<?> leave(Long bandId) {
        return null;
    }

    @Override
    public ResponseEntity<?> transferLeader(Long bandId, Object request) {
        return null;
    }
}
