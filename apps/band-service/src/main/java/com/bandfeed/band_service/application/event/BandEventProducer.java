package com.bandfeed.band_service.application.event;

import com.bandfeed.band_service.domain.event.BandCreatedEvent;
import com.bandfeed.band_service.domain.event.BandMemberJoinedEvent;

public interface BandEventProducer {
    void publishBandCreated(BandCreatedEvent event);
    void publishBandMemberJoined(BandMemberJoinedEvent event);
}
