package com.bandfeed.user_service.application.event;

import com.bandfeed.user_service.domain.event.UserCreatedEvent;
import com.bandfeed.user_service.domain.event.UserDeletedEvent;

public interface UserEventProducer {
    void publishUserCreated(UserCreatedEvent event);
    void publishUserDeleted(UserDeletedEvent event);
}
