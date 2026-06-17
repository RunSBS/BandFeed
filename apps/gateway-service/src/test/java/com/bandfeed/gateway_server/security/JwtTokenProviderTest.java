package com.bandfeed.gateway_server.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private static final String SECRET = "test-secret-key-for-testing-purposes-must-be-at-least-256-bits-long";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET);

    @Test
    void 유효한_토큰에서_userId를_추출한다() {
        UUID userId = UUID.randomUUID();
        String token = createToken(userId, 1000 * 60 * 60);

        Optional<String> result = jwtTokenProvider.getUserId(token);

        assertThat(result).contains(userId.toString());
    }

    @Test
    void 만료된_토큰은_빈값을_반환한다() {
        UUID userId = UUID.randomUUID();
        String token = createToken(userId, -1000);

        Optional<String> result = jwtTokenProvider.getUserId(token);

        assertThat(result).isEmpty();
    }

    @Test
    void 유효하지_않은_토큰은_빈값을_반환한다() {
        Optional<String> result = jwtTokenProvider.getUserId("invalid.token.value");

        assertThat(result).isEmpty();
    }

    private String createToken(UUID userId, long expirationMillis) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMillis))
                .signWith(key)
                .compact();
    }
}
