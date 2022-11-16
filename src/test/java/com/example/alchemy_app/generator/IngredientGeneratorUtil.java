package com.example.alchemy_app.generator;

import com.example.alchemy_app.entity.Ingredient;

public class IngredientGeneratorUtil {

    private IngredientGeneratorUtil() {}

    public static Ingredient createValidIngredient() {
        return Ingredient.builder().
                withPrice(100).
                withName(SecondaryGeneratorUtil.generateRndStr()).
                withLossProbability(((short) 5)).
                build();
    }

    public static Ingredient getFireIngredient() {
        return Ingredient.builder().
                withLossProbability(((short) 0)).
                withName("fire").
                withPrice(0).
                build();
    }
}
