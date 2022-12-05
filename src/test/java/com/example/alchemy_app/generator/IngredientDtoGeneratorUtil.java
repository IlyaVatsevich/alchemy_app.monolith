package com.example.alchemy_app.generator;

import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;

import java.util.List;

public class IngredientDtoGeneratorUtil {

    private IngredientDtoGeneratorUtil() {}

    public static IngredientCreateDto createBasicIngredientDto() {
        return IngredientCreateDto.builder().
                withLossProbability(((short) 0)).
                withPrice(0).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

    public static IngredientCreateDto createRecipeIngredientDto() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForValidLossProbability()).
                withPrice(SecondaryGeneratorUtil.generatePositiveInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                withIngredientIds(List.of(1000L,1100L)).
                build();
    }

    public static IngredientCreateDto createRecipeIngredientDtoWithNotExistingIngredients() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForValidLossProbability()).
                withPrice(SecondaryGeneratorUtil.generatePositiveInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                withIngredientIds(List.of(1L,2L)).
                build();
    }

    public static IngredientCreateDto createIngredientWithInvalidLossProbability() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForInvalidLossProbability()).
                withPrice(0).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

    public static IngredientCreateDto createIngredientWithInvalidPrice() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForValidLossProbability()).
                withPrice(SecondaryGeneratorUtil.generateNegativeInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

    public static IngredientCreateDto createBasicIngredientWithInvalidPriceAndLossProbability() {
        return IngredientCreateDto.builder().
                withLossProbability(((short) 5)).
                withPrice(SecondaryGeneratorUtil.generatePositiveInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                build();
    }

    public static IngredientCreateDto createIngredientWithIngredientsSizeEqualOne() {
        return IngredientCreateDto.builder().
                withLossProbability(SecondaryGeneratorUtil.generateShortForValidLossProbability()).
                withPrice(SecondaryGeneratorUtil.generatePositiveInteger()).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                withIngredientIds(List.of(1000L)).
                build();
    }

    public static IngredientCreateDto createIngredientWithAlreadyExistingName(String name) {
        return IngredientCreateDto.builder().
                withLossProbability(((short) 0)).
                withPrice(0).
                withName(name).
                build();
    }

}
