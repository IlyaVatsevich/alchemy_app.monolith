package com.example.alchemy_app.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@AllArgsConstructor
@Jacksonized
@Builder(setterPrefix = "with")
@Getter
public class MixResponse {

    private boolean isSuccessful;

    private List<Long> deletedIngredientIds;

    private IngredientResponseDto createdIngredient;

}
