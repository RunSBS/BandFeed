package com.bandfeed.band_service.infrastructure.messaging.kafka.producer;

import com.bandfeed.band_service.application.event.BandEventProducer;
import com.bandfeed.band_service.domain.event.BandCreatedEvent;
import com.bandfeed.band_service.domain.event.BandMemberJoinedEvent;
import org.springframework.stereotype.Component;

@Component
public class BandEventProducerImpl implements BandEventProducer {

    @Override
    public void publishBandCreated(BandCreatedEvent event) {
    }

    @Override
    public void publishBandMemberJoined(BandMemberJoinedEvent event) {
    }
}
