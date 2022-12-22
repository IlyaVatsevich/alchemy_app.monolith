package com.example.alchemy_app.api.impl;

import com.example.alchemy_app.api.IngredientControllerApiDescription;
import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;
import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import com.example.alchemy_app.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientControllerImpl implements IngredientControllerApiDescription {

    private final IngredientService ingredientService;

    @Override
    public ResponseEntity<IngredientCreateDto> createIngredient(IngredientCreateDto ingredientDto) {
        ingredientService.createIngredient(ingredientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientDto);
    }

    @Override
    public ResponseEntity<Page<IngredientResponseDto>> getAllIngredients(Pageable pageable) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(pageable));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredient(Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

}
