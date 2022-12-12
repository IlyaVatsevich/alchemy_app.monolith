package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.TokenResponse;
import com.example.alchemy_app.enums.TokenType;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenResponse mapTokens(String accessToken, String refreshToken) {
        return TokenResponse.builder().
                withAccessToken(accessToken).
                withRefreshToken(refreshToken).
                withTokenType(TokenType.BEARER).
                build();
    }

}
