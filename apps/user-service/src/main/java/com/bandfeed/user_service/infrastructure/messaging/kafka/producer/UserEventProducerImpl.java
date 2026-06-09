package com.bandfeed.user_service.infrastructure.messaging.kafka.producer;

import com.bandfeed.user_service.application.event.UserEventProducer;
import com.bandfeed.user_service.domain.event.UserCreatedEvent;
import com.bandfeed.user_service.domain.event.UserDeletedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducerImpl implements UserEventProducer {

    @Override
    public void publishUserCreated(UserCreatedEvent event) {
    }

    @Override
    public void publishUserDeleted(UserDeletedEvent event) {
    }
}
