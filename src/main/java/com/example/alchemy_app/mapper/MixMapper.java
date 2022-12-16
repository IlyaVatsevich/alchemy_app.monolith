package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MixMapper {

    private final IngredientMapper ingredientMapper;

    public MixResponse buildSuccessfulMixResponse(Ingredient ingredient) {
        return MixResponse.builder().withIsSuccessful(true).withCreatedIngredient(ingredientMapper.fromEntityToDto(ingredient)).build();
    }

    public MixResponse buildUnsuccessfulMixResponse(List<Long> ingredientIds) {
        return MixResponse.builder().withIsSuccessful(false).withDeletedIngredientIds(ingredientIds).build();
    }
}
