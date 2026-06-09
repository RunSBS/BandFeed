package com.bandfeed.band_service.infrastructure.client.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProviderImpl {

    private final UserClient userClient;

}
