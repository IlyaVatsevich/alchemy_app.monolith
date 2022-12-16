package com.example.alchemy_app.generator;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;

import java.util.List;

public class MixSellBuyGeneratorUtil {

    private MixSellBuyGeneratorUtil() {}

    public static MixDto generateMixDto() {
        return MixDto.builder().
                withIngredientsIds(List.of(1000L,1200L)).
                build();
    }

    public static MixDto generateMixDto(List<Long> ingredientIds) {
        return MixDto.builder().
                withIngredientsIds(ingredientIds).
                build();
    }

    public static SellDto generateSellDto() {
        return SellDto.builder().withIngredientId(1000L).withCount(1).build();
    }

    public static SellDto generateSellDto(Long ingredientId,int count) {
        return SellDto.builder().withIngredientId(ingredientId).withCount(count).build();
    }

    public static BuyDto generateBuyDto() {
        return BuyDto.builder().withCount(1).withIngredientId(1000L).build();
    }

    public static BuyDto generateBuyDto(Long ingredientId,int count) {
        return BuyDto.builder().withCount(count).withIngredientId(ingredientId).build();
    }

}
