package com.example.alchemy_app.dto;

import com.example.alchemy_app.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private TokenType tokenType;

}
