package com.bandfeed.wiki_service.infrastructure.client.spotify;

import org.springframework.stereotype.Component;

// OAuth2 Client Credentials 방식으로 Spotify 액세스 토큰을 관리하는 컴포넌트
// client_id / client_secret 을 이용해 토큰을 발급받고, 만료 시 자동 갱신
@Component
public class SpotifyTokenManager {
}
