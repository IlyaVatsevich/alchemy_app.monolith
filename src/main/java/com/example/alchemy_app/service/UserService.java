package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.TokenRequest;
import com.example.alchemy_app.dto.TokenResponse;
import com.example.alchemy_app.dto.UserLogDto;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.dto.HighScoreTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {

    void registration(@Valid UserRegDto userRegDto);

    TokenResponse login(@Valid UserLogDto userLogDto);

    Page<HighScoreTable> getUsersByMaxGold(Pageable pageable);

    Page<HighScoreTable> getUsersByMaxUserIngredientCount(Pageable pageable);

    Page<UserIngredientDto> getUserIngredient(Pageable pageable);

    TokenResponse getNewAccessAndRefreshTokenByRefreshToken(TokenRequest tokenRequest);

}
