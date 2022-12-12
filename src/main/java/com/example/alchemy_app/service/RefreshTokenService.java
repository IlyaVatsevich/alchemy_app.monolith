package com.example.alchemy_app.service;

import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken findByToken(String token);

    RefreshToken updateUsedRefreshToken(RefreshToken refreshToken);

}
