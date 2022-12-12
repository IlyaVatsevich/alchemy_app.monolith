package com.example.alchemy_app.mapper;

import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class RefreshTokenMapper {

    @Value("${security-custom.refresh-token.expiration}")
    private long expireDate;

    public RefreshToken buildRefreshToken(User user) {
        return RefreshToken.builder().
                withToken(generateToken()).
                withUser(user).
                withExpiryDate(mapExpireDate()).
                build();
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        refreshToken.setExpiryDate(mapExpireDate());
        refreshToken.setToken(generateToken());
    }

    private LocalDateTime mapExpireDate() {
        return LocalDateTime.ofInstant(Instant.now().plusMillis(expireDate), ZoneId.systemDefault());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
