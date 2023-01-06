package com.example.alchemy_app.api.impl;

import com.example.alchemy_app.api.UserIngredientControllerApiDescription;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserIngredientControllerImpl implements UserIngredientControllerApiDescription {

    private final UserIngredientService userIngredientService;

    @Override
    public ResponseEntity<Page<UserIngredientDto>> getUserIngredients(Pageable pageable) {
        return ResponseEntity.ok(userIngredientService.getUserIngredient(pageable));
    }
}
