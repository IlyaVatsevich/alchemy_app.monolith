package com.example.alchemy_app.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class IngredientResponseDto {

    private String name;

    private int price;

    private int lossProbability;

    private List<IngredientResponseDto> ingredients;

}
