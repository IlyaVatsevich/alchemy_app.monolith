package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.mapper.RefreshTokenMapper;
import com.example.alchemy_app.repository.RefreshTokenRepository;
import com.example.alchemy_app.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        Optional<RefreshToken> refreshTokenByUser = refreshTokenRepository.findRefreshTokenByUser(user);
        if (refreshTokenByUser.isPresent()) {
            RefreshToken refreshToken = refreshTokenByUser.get();
            refreshTokenMapper.updateRefreshToken(refreshToken);
            return refreshTokenRepository.save(refreshToken);
        }
        return refreshTokenRepository.save(refreshTokenMapper.buildRefreshToken(user));
    }

    @Override
    public RefreshToken findByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByToken(token).
                orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Token not exist."));
        verifyExpiration(refreshToken);
        return refreshToken;
    }

    private void verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new CredentialsExpiredException("Token is expired.");
        }
    }
}
