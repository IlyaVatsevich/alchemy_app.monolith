package com.example.alchemy_app.dto;

import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder(setterPrefix = "with")
@Getter
@Jacksonized
@AllArgsConstructor
public class UserIngredientDto {

    private IngredientResponseDto ingredientResponseDto;

    private int count;
}
