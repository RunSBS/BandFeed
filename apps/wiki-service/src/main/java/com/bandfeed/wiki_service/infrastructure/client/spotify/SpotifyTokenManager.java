package com.bandfeed.wiki_service.infrastructure.client.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

// OAuth2 Client Credentials 방식으로 Spotify 액세스 토큰을 관리하는 컴포넌트
// client_id / client_secret 을 이용해 토큰을 발급받고, 만료 시 자동 갱신
@Slf4j
@Component
public class SpotifyTokenManager {

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    private final RestClient restClient;
    private final String clientId;
    private final String clientSecret;

    private volatile String accessToken;
    private volatile Instant expiresAt = Instant.MIN;

    public SpotifyTokenManager(
            @Value("${spotify.client-id}") String clientId,
            @Value("${spotify.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restClient = RestClient.create();
    }

    public synchronized String getAccessToken() {
        if (accessToken == null || !Instant.now().isBefore(expiresAt)) {
            refreshToken();
        }
        return accessToken;
    }

    private void refreshToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        TokenResponse response = restClient.post()
                .uri(TOKEN_URL)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeCredentials())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(TokenResponse.class);

        this.accessToken = response.getAccessToken();
        this.expiresAt = Instant.now().plusSeconds(response.getExpiresIn() - 60);
        log.info("Spotify access token refreshed. expiresIn={}s", response.getExpiresIn());
    }

    private String encodeCredentials() {
        String credentials = clientId + ":" + clientSecret;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("expires_in")
        private int expiresIn;
    }
}
