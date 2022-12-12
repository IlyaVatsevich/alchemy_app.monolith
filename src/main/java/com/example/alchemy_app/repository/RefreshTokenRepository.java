package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.RefreshToken;
import com.example.alchemy_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findRefreshTokenByToken(String token);

    Optional<RefreshToken> findRefreshTokenByUser(User user);



}
