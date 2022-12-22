package com.example.alchemy_app.api.impl;

import com.example.alchemy_app.api.UserControllerApiDescription;
import com.example.alchemy_app.dto.TokenRequest;
import com.example.alchemy_app.dto.TokenResponse;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.dto.UserLogDto;
import com.example.alchemy_app.dto.UserRegDto;
import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserControllerApiDescription {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> registration(UserRegDto userRegDto) {
        userService.registration(userRegDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Page<HighScoreTable>> getUsersByMaxGold(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMaxGold(pageable));
    }

    @Override
    public ResponseEntity<TokenResponse> login(UserLogDto userLogDto) {
        return ResponseEntity.ok(userService.login(userLogDto));
    }

    @Override
    public ResponseEntity<Page<HighScoreTable>> getUsersByMaxCount(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMaxUserIngredientCount(pageable));
    }

    @Override
    public ResponseEntity<Page<UserIngredientDto>> getUserIngredient(Pageable pageable) {
        return ResponseEntity.ok(userService.getUserIngredient(pageable));
    }

    @Override
    public ResponseEntity<TokenResponse> getNewAccessAndRefreshTokenByRefreshToken(TokenRequest tokenRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getNewAccessAndRefreshTokenByRefreshToken(tokenRequest));
    }
}
