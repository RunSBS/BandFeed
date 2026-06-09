package com.bandfeed.wiki_service.infrastructure.messaging.kafka.consumer;

import com.bandfeed.wiki_service.application.event.UserEventConsumer;
import org.springframework.stereotype.Component;

@Component
public class UserEventListenerImpl implements UserEventConsumer {
    // @KafkaListener(topics = "user-events", groupId = "wiki-service")
}
