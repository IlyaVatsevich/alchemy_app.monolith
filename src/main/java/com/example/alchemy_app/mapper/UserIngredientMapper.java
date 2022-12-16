package com.example.alchemy_app.mapper;

import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIngredientMapper {

    private final IngredientMapper ingredientMapper;

    public UserIngredient buildUserIngredient(Ingredient ingredient, User user,int count) {
        return UserIngredient.builder().
                withId(buildUserIngredientId(ingredient.getId(),user.getId())).
                withIngredient(ingredient).
                withUser(user).
                withCount(count).
                build();
    }

    public UserIngredientDto mapUserIngredientDtoFromUserIngredient(UserIngredient userIngredient) {
        return UserIngredientDto.builder().
                withIngredient(mapIngredient(userIngredient.getIngredient())).
                withCount(userIngredient.getCount()).
                build();
    }

    private UserIngredient.UserIngredientId buildUserIngredientId(Long ingredientId,Long userId) {
        return UserIngredient.UserIngredientId.builder().
                withIngredientId(ingredientId).
                withUserId(userId).build();
    }

    private IngredientResponseDto mapIngredient(Ingredient ingredient) {
        return ingredientMapper.fromEntityToDto(ingredient);
    }
}
