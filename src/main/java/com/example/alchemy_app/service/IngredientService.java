package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;
import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IngredientService {

    void createIngredient(@Valid IngredientCreateDto ingredientDto);

    Page<IngredientResponseDto> getAllIngredients(Pageable pageable);

    IngredientResponseDto getIngredientById(Long id);


}
