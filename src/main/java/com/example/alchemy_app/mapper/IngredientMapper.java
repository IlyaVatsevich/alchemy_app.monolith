package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;
import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import com.example.alchemy_app.entity.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {

    public Ingredient fromDtoToEntity(IngredientCreateDto ingredientDto) {
        return Ingredient.builder().withName(ingredientDto.getName()).
                withPrice(ingredientDto.getPrice()).
                withLossProbability(ingredientDto.getLossProbability()).
                build();
    }

    public IngredientResponseDto fromEntityToDto(Ingredient ingredient) {
        return IngredientResponseDto.builder().
                withIngredients(mapIngredientDtos(ingredient.getIngredients())).
                withName(ingredient.getName()).
                withPrice(ingredient.getPrice()).
                withLossProbability(ingredient.getLossProbability()).
                build();
    }

    private List<IngredientResponseDto> mapIngredientDtos(List<Ingredient> ingredients) {
        return ingredients.stream().map(this::fromEntityToDto).collect(Collectors.toUnmodifiableList());
    }

}
