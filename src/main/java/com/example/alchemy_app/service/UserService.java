package com.example.alchemy_app.service;

import com.example.alchemy_app.annotation.pageable.ValidSortProperty;
import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.dto.TokenRequest;
import com.example.alchemy_app.dto.TokenResponse;
import com.example.alchemy_app.dto.UserLogDto;
import com.example.alchemy_app.dto.UserRegDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {

    TokenResponse registration(@Valid UserRegDto userRegDto);

    TokenResponse login(@Valid UserLogDto userLogDto);

    Page<HighScoreTable> getUsersByMaxGold(@Valid
                                           @ValidSortProperty(allowedProperties = {"score","login"}) Pageable pageable);

    Page<HighScoreTable> getUsersByMaxUserIngredientCount(@Valid
                                                          @ValidSortProperty(allowedProperties = {"score","login"}) Pageable pageable);

    TokenResponse getNewAccessAndRefreshTokenByRefreshToken(@Valid TokenRequest tokenRequest);

}
